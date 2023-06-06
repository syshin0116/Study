import numpy as np
import pandas as pd
import datetime
import requests
import time

import pyupbit


# ---------------------------------------------------------------------------------
# DO NOT EDIT
# ---------------------------------------------------------------------------------
#
# trading_helper.py
# 
# ---------------------------------------------------------------------------------

TRADING_HELPER_VERSION = '2023.05.15-v1'


global_auth_code = ''

# set_auth_code() AUTH_CODE를 저장
#
# auth_code - AUTH CODE
#
def set_auth_code(auth_code):
    global_auth_code = auth_code

    URL = 'https://45d75z9xcc.execute-api.ap-northeast-2.amazonaws.com/live/ycs-admin'

    params = { 'trading_log': 1 }
    data = { 'auth_code': auth_code, 'log': '' }
    
    r = requests.post( URL, params=params, data=data )

    try:
        
        if r.status_code == 200:

            response = r.json()

            if response.get('result') == 'success':
                print( f"Log updated! ({response.get('semester')} {response.get('class_id')} {response.get('user_name')})")

        else:
            print("Log failed.")

    except Exception as e:
        print('Exception: ' + e)
        sys.exit()




# get_ohlcv2() 시작과 끝 날짜를 지정하여 가격정보를 가져오는 함수 
#
# ticker - 암호화폐 티커
# date1 - 시작 날짜 YYYYMMDD 형식
# date2 - 종료 날짜 YYYYMMDD 형식
#
def get_ohlcv2(ticker, date1, date2):

    try:

        df = pd.read_csv(f'https://esohn.be/semester/2023-1/dfs/{ticker}_{date1}_{date2}.csv')

        return df

    except:

        print('Retrieving...')

        d1 = datetime.datetime.strptime( date1, "%Y%m%d" )
        d2 = datetime.datetime.strptime( date2, "%Y%m%d" )

        result_df = pd.DataFrame()

        num_days = (d2 - d1).days

        end_date = d2

        if end_date > datetime.datetime.today():
            end_date = datetime.datetime.today()

        while num_days > 0:

            if num_days <= 200:
                start_date = end_date - datetime.timedelta(days = num_days)
                df1 = pyupbit.get_ohlcv( ticker, to = end_date.strftime("%Y%m%d"), interval='day1', count= num_days)
                num_days = 0
            else:
                start_date = end_date - datetime.timedelta(days = 200)
                df1 = pyupbit.get_ohlcv( ticker, to = end_date.strftime("%Y%m%d"), interval='day1', count= 200)
                num_days -= 200

            end_date = start_date

            result_df = pd.concat( [df1, result_df] )

        return result_df


# get_multiple()
# 시작과 끝 날짜를 지정하여 Rebalancing 에 사용될 다음 암호화폐 4개의 가격정보를 가져옴:
# 비트코인 (BTC)
# 이더리움 (ETH)
# 도지코인 (DOGE)
# 에이다 (ADA)
#
# date1 - 시작 날짜 YYYYMMDD 형식
# date2 - 종료 날짜 YYYYMMDD 형식
#
def get_multiple(date1, date2):

    try:

        df = pd.read_csv(f'https://esohn.be/semester/2023-1/dfs/multiple_{date1}_{date2}.csv')

        return df

    except:

        print('Retrieving...')

        df_btc = get_ohlcv2('KRW-BTC', date1=date1, date2=date2)
        time.sleep(0.2)
        df_eth = get_ohlcv2('KRW-ETH', date1=date1, date2=date2)
        time.sleep(0.2)
        df_doge = get_ohlcv2('KRW-DOGE', date1=date1, date2=date2)
        time.sleep(0.2)
        df_ada = get_ohlcv2('KRW-ADA', date1=date1, date2=date2)

        df = pd.DataFrame()

        df['price_btc'] = df_btc['open']
        df['price_eth'] = df_eth['open']
        df['price_doge'] = df_doge['open']
        df['price_ada'] = df_ada['open']

        df['bal_krw'] = 0
        df['bal_btc'] = 0
        df['bal_eth'] = 0
        df['bal_doge'] = 0
        df['bal_ada'] = 0

        df['bal2_krw'] = 0
        df['bal2_btc'] = 0
        df['bal2_eth'] = 0
        df['bal2_doge'] = 0
        df['bal2_ada'] = 0

        df['target_btc'] = 0
        df['target_eth'] = 0
        df['target_doge'] = 0
        df['target_ada'] = 0

        df['trade_btc'] = 0
        df['trade_eth'] = 0
        df['trade_doge'] = 0
        df['trade_ada'] = 0

        df['value'] = 0

        df.reset_index(inplace=True)

        return df



# check_performance_vol() 
#
# 변동성돌파 전략의 기간수익률(HPR) 및 최대낙폭(MDD)을 구하는 함수
#
# df - volatility_breakout()을 거친 데이터 프레임
# fee - 매매 수수료, 기본값은 0.0002
#
def check_performance_vol( df, fee = 0.0002 ):

    # k 음수인 경우 NaN 반환
    df['invalid'] = np.where( df['target'] >= df['open'], False, df['target'].notnull() )
    if df['invalid'].sum() > 0:
        return np.NaN, np.NaN

    # ROR (Rate of Return: 수익률) 
    # 목표 매수가에 도달했다면 + trade열이 True라면 목표 매수가에 매수 후 종가에 매도 - 수수료
    # 그렇지 않다면 매매하지 않은 그대로 1
    df['ror'] = np.where( df['cond1'] & df['cond2'], df['close'] / df['target'] - fee, 1)

    # HPR (Holding Period Return: 기간수익률)
    # 기간 전체의 ror을 곱한 값
    df['hpr'] = df['ror'].cumprod()

    # DD (Drawdown: 낙폭)
    # (현재까지 가장 큰 HPR - 현재 HPR ) / 현재까지 가장 큰 HPR
    df['dd'] = (df['hpr'].cummax() - df['hpr']) / df['hpr'].cummax()

    # 전날까지의 값을 마지막으로 HPR 계산
    hpr = df['hpr'][ len(df)-2 ] -1
    
    # 최대 낙폭
    mdd = df['dd'].max()

    return hpr, mdd


# check_performance_rebal() 
#
# Rebalancing 전략의 기간수익률(HPR) 및 최대낙폭(MDD)을 구하는 함수
#
# df - rebalancing()을 거친 데이터 프레임
#
def check_performance_rebal( df ):

    # ROR (Rate of Return: 수익률) 
    # 현재 가치 / 전날 가치
    df['ror'] = df['value'] / df['value'].shift(1)

    # HPR (Holding Period Return: 기간수익률)
    # 기간 전체의 ror을 곱한 값
    df['hpr'] = df['ror'].cumprod()

    # DD (Drawdown: 낙폭)
    # (현재까지 가장 큰 HPR - 현재 HPR ) / 현재까지 가장 큰 HPR
    df['dd'] = (df['hpr'].cummax() - df['hpr']) / df['hpr'].cummax()

    # 전날까지의 값을 마지막으로 HPR 계산
    hpr = df['hpr'][ len(df)-2 ] - 1

    # 최대 낙폭
    mdd = df['dd'].max()

    return hpr, mdd

