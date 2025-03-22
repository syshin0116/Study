import discord
import logging
from dotenv import find_dotenv, load_dotenv
import os
import json
import random
import asyncio
from discord.ext import commands

load_dotenv(find_dotenv())
DISCORD_TOKEN = os.getenv("DISCORD_TOKEN")
handler = logging.FileHandler(filename="discord.log", encoding="utf-8", mode="w")

DATA_PATH = "./data.json"

with open(DATA_PATH) as f:
    data = json.load(f)

description = (
    """해리포터 마법 대결 - 마법 주문을 사용하고 생물을 소환하여 전투하세요!"""
)

intents = discord.Intents.default()
intents.members = True
intents.message_content = True

bot = commands.Bot(command_prefix="?", description=description, intents=intents)

# 전투 진행 중인 채널 추적
active_battles = {}  # 플레이어 vs 플레이어 전투


# 게임 관련 함수들
def save_data():
    with open(DATA_PATH, "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)


def get_player(player_name):
    for player_data in data["players"]:
        if player_name in player_data:
            return player_data[player_name]
    return None


def get_spell(spell_name):
    for spell_data in data["spells"]:
        if spell_name in spell_data:
            return spell_data[spell_name]
    return None


def get_summonable(creature_name):
    for creature_data in data["summonable_creatures"]:
        if creature_name in creature_data:
            return creature_data[creature_name]
    return None


@bot.event
async def on_ready():
    print(f"해리포터 마법 대결 봇이 준비되었습니다! {bot.user} (ID: {bot.user.id})")
    print("------")


@bot.command(name="도움말")
async def help_command(ctx):
    embed = discord.Embed(title="해리포터 마법 대결 도움말", color=discord.Color.blue())
    embed.add_field(
        name="?등록 [이름]", value="새로운 마법사로 등록합니다.", inline=False
    )
    embed.add_field(
        name="?정보 [이름]", value="마법사의 정보를 확인합니다.", inline=False
    )
    embed.add_field(
        name="?주문목록", value="사용 가능한 마법 주문을 확인합니다.", inline=False
    )
    embed.add_field(
        name="?결투신청 [상대방]",
        value="다른 마법사와의 결투를 신청합니다.",
        inline=False,
    )
    embed.add_field(
        name="?주문 [주문이름]", value="결투 중 주문을 사용합니다.", inline=False
    )
    embed.add_field(
        name="?소환 [생물이름]", value="결투 중 마법 생물을 소환합니다.", inline=False
    )
    await ctx.send(embed=embed)


@bot.command(name="등록")
async def register(ctx, name: str):
    # 이미 존재하는 플레이어인지 확인
    if get_player(name):
        await ctx.send(f"{name} 마법사는 이미 등록되어 있습니다.")
        return

    # 새 플레이어 추가
    houses = ["그리핀도르", "슬리데린", "래번클로", "후플푸프"]
    new_player = {
        name: {
            "health": 100,
            "mana": 100,
            "attack": 10,
            "defense": 10,
            "house": random.choice(houses),
            "summoned": None,
        }
    }
    data["players"].append(new_player)
    save_data()

    await ctx.send(
        f"{name} 마법사가 등록되었습니다! '?정보 {name}'으로 정보를 확인하세요."
    )


@bot.command(name="정보")
async def info(ctx, name: str):
    player = get_player(name)
    if not player:
        await ctx.send(f"{name} 마법사를 찾을 수 없습니다.")
        return

    embed = discord.Embed(title=f"{name}의 정보", color=discord.Color.green())
    embed.add_field(name="기숙사", value=player["house"], inline=True)
    embed.add_field(name="체력", value=f"{player['health']}/100", inline=True)
    embed.add_field(name="마나", value=f"{player['mana']}/100", inline=True)
    embed.add_field(name="공격력", value=player["attack"], inline=True)
    embed.add_field(name="방어력", value=player["defense"], inline=True)

    if player["summoned"]:
        embed.add_field(name="소환수", value=player["summoned"], inline=False)

    await ctx.send(embed=embed)


@bot.command(name="주문목록")
async def spell_list(ctx):
    embed = discord.Embed(title="사용 가능한 마법 주문", color=discord.Color.blue())

    for spell_data in data["spells"]:
        for spell_name, info in spell_data.items():
            embed.add_field(
                name=f"{spell_name} (쿨다운: {info['cooldown']}턴)",
                value=f"종류: {info['type']}, {info['description']}",
                inline=False,
            )

    await ctx.send(embed=embed)


@bot.command(name="결투신청")
async def duel_request(ctx, opponent: str):
    challenger = ctx.author.name

    if challenger == opponent:
        await ctx.send("자기 자신과 결투할 수 없습니다!")
        return

    if ctx.channel.id in active_battles:
        await ctx.send("이미 이 채널에서 결투가 진행 중입니다.")
        return

    challenger_data = get_player(challenger)
    opponent_data = get_player(opponent)

    if not challenger_data:
        await ctx.send(f"{challenger}님은 먼저 '?등록 {challenger}'으로 등록해주세요.")
        return

    if not opponent_data:
        await ctx.send(f"{opponent} 마법사를 찾을 수 없습니다.")
        return

    # 결투 시작
    active_battles[ctx.channel.id] = {
        "players": [challenger, opponent],
        "current_turn": 0,
        "turn_count": 0,
    }

    embed = discord.Embed(title="마법 결투!", color=discord.Color.red())
    embed.add_field(
        name=challenger, value=f"체력: {challenger_data['health']}", inline=True
    )
    embed.add_field(
        name=opponent, value=f"체력: {opponent_data['health']}", inline=True
    )
    embed.set_footer(
        text=f"{challenger}의 차례입니다. '?주문 [주문이름]' 또는 '?소환 [생물이름]'을 사용하세요."
    )

    await ctx.send(f"{opponent}님과의 결투가 시작됩니다!", embed=embed)


@bot.command(name="주문")
async def cast_spell(ctx, spell_name: str):
    player_name = ctx.author.name

    if ctx.channel.id not in active_battles:
        await ctx.send("진행 중인 결투가 없습니다.")
        return

    battle = active_battles[ctx.channel.id]
    current_player = battle["players"][battle["current_turn"]]

    if player_name != current_player:
        await ctx.send("당신의 차례가 아닙니다.")
        return

    player = get_player(player_name)
    opponent_name = battle["players"][1 if battle["current_turn"] == 0 else 0]
    opponent = get_player(opponent_name)

    spell = get_spell(spell_name)
    if not spell:
        await ctx.send(
            f"'{spell_name}'은(는) 존재하지 않는 주문입니다. '?주문목록'으로 사용 가능한 주문을 확인하세요."
        )
        return

    # 마나 확인
    if player["mana"] < 20:  # 기본 마나 소모량
        await ctx.send(f"마나가 부족합니다! (현재 마나: {player['mana']})")
        return

    # 주문 효과 적용
    if spell["type"] == "공격":
        final_damage = max(1, spell["power"] - opponent["defense"] // 2)
        opponent["health"] -= final_damage
        player["mana"] -= 20
        await ctx.send(
            f"{player_name}님이 {spell_name} 주문을 사용했습니다! {opponent_name}에게 {final_damage}의 피해를 입혔습니다."
        )

    elif spell["type"] == "치유":
        heal = spell["power"]
        player["health"] = min(100, player["health"] + heal)
        player["mana"] -= 20
        await ctx.send(
            f"{player_name}님이 {spell_name} 주문으로 {heal}만큼 체력을 회복했습니다!"
        )

    elif spell["type"] == "소환":
        if "summon" in spell:
            player["summoned"] = spell["summon"]
            player["mana"] -= 30  # 소환 주문은 더 많은 마나 소모
            await ctx.send(f"{player_name}님이 {spell['summon']}을(를) 소환했습니다!")

    save_data()

    # 승패 확인
    if opponent["health"] <= 0:
        del active_battles[ctx.channel.id]
        await ctx.send(f"🏆 {player_name}님이 결투에서 승리했습니다!")
        return

    # 턴 교체
    battle["current_turn"] = 1 if battle["current_turn"] == 0 else 0
    battle["turn_count"] += 1

    # 마나 회복 (턴마다 10씩)
    player["mana"] = min(100, player["mana"] + 10)
    opponent["mana"] = min(100, opponent["mana"] + 10)
    save_data()

    # 현재 상태 표시
    embed = discord.Embed(
        title=f"결투 진행 중 (턴 {battle['turn_count']})", color=discord.Color.blue()
    )
    embed.add_field(
        name=player_name,
        value=f"체력: {player['health']}\n마나: {player['mana']}",
        inline=True,
    )
    embed.add_field(
        name=opponent_name,
        value=f"체력: {opponent['health']}\n마나: {opponent['mana']}",
        inline=True,
    )
    next_player = battle["players"][battle["current_turn"]]
    embed.set_footer(text=f"{next_player}의 차례입니다.")

    await ctx.send(embed=embed)


@bot.command(name="소환")
async def summon_creature(ctx, creature_name: str):
    player_name = ctx.author.name

    if ctx.channel.id not in active_battles:
        await ctx.send("진행 중인 결투가 없습니다.")
        return

    battle = active_battles[ctx.channel.id]
    if player_name != battle["players"][battle["current_turn"]]:
        await ctx.send("당신의 차례가 아닙니다.")
        return

    player = get_player(player_name)
    creature = get_summonable(creature_name)

    if not creature:
        await ctx.send(f"{creature_name}은(는) 소환할 수 없는 생물입니다.")
        return

    # 마나 확인
    if player["mana"] < 40:  # 소환은 더 많은 마나가 필요
        await ctx.send(
            f"마나가 부족합니다! (현재 마나: {player['mana']}, 필요 마나: 40)"
        )
        return

    # 생물 소환
    player["summoned"] = creature_name
    player["mana"] -= 40
    save_data()

    await ctx.send(f"{player_name}님이 {creature_name}을(를) 소환했습니다!")

    # 턴 교체
    battle["current_turn"] = 1 if battle["current_turn"] == 0 else 0
    battle["turn_count"] += 1

    # 마나 회복 (턴마다 10씩)
    player["mana"] = min(100, player["mana"] + 10)

    # 현재 상태 표시
    opponent_name = battle["players"][1 if battle["current_turn"] == 0 else 0]
    opponent = get_player(opponent_name)

    embed = discord.Embed(
        title=f"결투 진행 중 (턴 {battle['turn_count']})", color=discord.Color.blue()
    )
    embed.add_field(
        name=player_name,
        value=f"체력: {player['health']}\n마나: {player['mana']}\n소환수: {creature_name}",
        inline=True,
    )
    embed.add_field(
        name=opponent_name,
        value=f"체력: {opponent['health']}\n마나: {opponent['mana']}",
        inline=True,
    )
    next_player = battle["players"][battle["current_turn"]]
    embed.set_footer(text=f"{next_player}의 차례입니다.")

    await ctx.send(embed=embed)


bot.run(DISCORD_TOKEN, log_handler=handler, reconnect=True)
