const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

canvas.width = 800;
canvas.height = 400;

// Game state
let score = 7420;
let hiScore = 7420;
let distance = 713; // Distance in 0.1km units
let timeLeft = 58;
let gameSpeed = 3;
let isGameOver = false;
let stage = 1;

// Colors
const COLORS = {
    SKY: '#40E0D0',  // Turquoise blue like in the image
    MOUNTAIN: '#3498db',
    GROUND: '#FFFFFF',
    PLATFORM: '#0000FF',
    PENGUIN: '#000000'
};

// Player
const player = {
    x: canvas.width / 2,
    y: canvas.height - 80,
    width: 30,
    height: 30,
    velocityX: 0,
    speed: 5
};

// Platforms
let platforms = [];
const PLATFORM_WIDTH = 100;
const PLATFORM_HEIGHT = 10;

// Clouds
let clouds = [];
const CLOUD_COUNT = 3;

// Initialize clouds
function initClouds() {
    clouds = [];
    for (let i = 0; i < CLOUD_COUNT; i++) {
        clouds.push({
            x: Math.random() * canvas.width,
            y: 30 + Math.random() * 50,
            width: 40,
            speed: 0.5 + Math.random() * 0.5
        });
    }
}

// Spawn platform
function spawnPlatform() {
    if (Math.random() < 0.02) {
        platforms.push({
            x: canvas.width / 2 + (Math.random() - 0.5) * 200,
            y: -20,
            width: PLATFORM_WIDTH,
            height: PLATFORM_HEIGHT
        });
    }
}

// Game controls
document.addEventListener('keydown', (event) => {
    switch (event.code) {
        case 'ArrowLeft':
            player.velocityX = -player.speed;
            break;
        case 'ArrowRight':
            player.velocityX = player.speed;
            break;
    }
});

document.addEventListener('keyup', (event) => {
    if (event.code === 'ArrowLeft' && player.velocityX < 0 ||
        event.code === 'ArrowRight' && player.velocityX > 0) {
        player.velocityX = 0;
    }
});

// Update game state
function update() {
    if (isGameOver) return;

    // Update player
    player.x += player.velocityX;
    player.x = Math.max(50, Math.min(canvas.width - 50, player.x));

    // Update clouds
    clouds.forEach(cloud => {
        cloud.x -= cloud.speed;
        if (cloud.x + cloud.width < 0) {
            cloud.x = canvas.width;
            cloud.y = 30 + Math.random() * 50;
        }
    });

    // Update platforms
    spawnPlatform();
    platforms.forEach((platform, index) => {
        platform.y += gameSpeed * 2;

        if (platform.y > canvas.height) {
            platforms.splice(index, 1);
        }
    });

    // Update game progress
    distance = Math.max(0, distance - 0.1);
    timeLeft = Math.max(0, timeLeft - 0.016);

    // Check game over conditions
    if (timeLeft <= 0) {
        gameOver();
    }
}

// Draw game elements
function draw() {
    // Draw sky
    ctx.fillStyle = COLORS.SKY;
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    // Draw clouds
    ctx.fillStyle = '#FFFFFF';
    clouds.forEach(cloud => {
        ctx.beginPath();
        ctx.arc(cloud.x, cloud.y, 20, 0, Math.PI * 2);
        ctx.arc(cloud.x + 15, cloud.y, 15, 0, Math.PI * 2);
        ctx.arc(cloud.x + 30, cloud.y, 20, 0, Math.PI * 2);
        ctx.fill();
    });

    // Draw mountains
    ctx.fillStyle = COLORS.MOUNTAIN;
    ctx.beginPath();
    ctx.moveTo(0, 150);
    for (let i = 0; i < canvas.width; i += 50) {
        ctx.lineTo(i, 150 - Math.sin(i / 30) * 20);
    }
    ctx.lineTo(canvas.width, 150);
    ctx.lineTo(canvas.width, 170);
    ctx.lineTo(0, 170);
    ctx.fill();

    // Draw ground gradient
    const groundGradient = ctx.createLinearGradient(0, canvas.height - 150, 0, canvas.height);
    groundGradient.addColorStop(0, '#FFFFFF');
    groundGradient.addColorStop(1, '#E0E0E0');
    ctx.fillStyle = groundGradient;
    ctx.fillRect(0, canvas.height - 150, canvas.width, 150);

    // Draw platforms
    ctx.fillStyle = COLORS.PLATFORM;
    platforms.forEach(platform => {
        ctx.fillRect(platform.x - platform.width / 2, platform.y, platform.width, platform.height);
    });

    // Draw player (penguin)
    ctx.fillStyle = COLORS.PENGUIN;
    ctx.beginPath();
    // Body
    ctx.fillRect(player.x - 15, player.y, 30, 30);
    // Wing
    ctx.fillRect(player.x - 20, player.y + 10, 5, 15);

    // Draw UI
    ctx.fillStyle = '#000000';
    ctx.font = '16px "Press Start 2P"';
    ctx.textAlign = 'left';

    // Format score and hi-score with leading zeros
    const scoreStr = score.toString().padStart(6, '0');
    const hiScoreStr = hiScore.toString().padStart(6, '0');

    ctx.fillText(`${scoreStr} HI-${hiScoreStr}`, 10, 30);
    ctx.fillText(`TIME-${Math.ceil(timeLeft).toString().padStart(4, '0')}`, 10, 60);
    ctx.fillText(`REST ${distance.toFixed(1)}km`, canvas.width - 200, 30);
    ctx.fillText(`STAGE-${stage.toString().padStart(2, '0')}`, canvas.width - 200, 60);

    if (isGameOver) {
        ctx.fillStyle = 'rgba(0, 0, 0, 0.5)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = '#FFFFFF';
        ctx.font = '24px "Press Start 2P"';
        ctx.textAlign = 'center';
        ctx.fillText('GAME OVER', canvas.width / 2, canvas.height / 2);
        ctx.font = '16px "Press Start 2P"';
        ctx.fillText('Press SPACE to restart', canvas.width / 2, canvas.height / 2 + 40);
    }
}

function gameOver() {
    isGameOver = true;
    document.addEventListener('keydown', restartGame);
}

function restartGame(event) {
    if (event.code === 'Space' && isGameOver) {
        isGameOver = false;
        score = 7420;
        distance = 713;
        timeLeft = 58;
        gameSpeed = 3;
        stage = 1;
        player.x = canvas.width / 2;
        player.velocityX = 0;
        platforms = [];
        document.removeEventListener('keydown', restartGame);
    }
}

// Game loop
function gameLoop() {
    update();
    draw();
    requestAnimationFrame(gameLoop);
}

// Initialize and start game
initClouds();
gameLoop();
