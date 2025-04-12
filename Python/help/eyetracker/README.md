# 아이트래커 (Eye Tracker) 애플리케이션

사용자의 시선 추적 데이터를 수집하고 분석하는 Django 웹 애플리케이션입니다.

## 기능

- 사용자 인증 및 프로필 관리
- 시선 추적 세션 생성 및 관리
- 실시간 시선 추적 및 데이터 수집
- 히트맵 생성 및 시각화
- 데이터 분석 및 통계

## 기술 스택

- Python 3.8+
- Django 4.2+
- OpenCV
- NumPy, SciPy
- HTML, CSS, JavaScript
- Bootstrap 5

## 설치 방법

1. 저장소 클론:
```bash
git clone https://github.com/yourusername/eyetracker.git
cd eyetracker
```

2. 의존성 설치:
```bash
# 가상 환경 생성 (선택 사항)
python -m venv venv
source venv/bin/activate  # Linux/macOS
# 또는
venv\Scripts\activate  # Windows

# uv로 의존성 설치
uv pip install -r requirements.txt
```

3. 데이터베이스 마이그레이션:
```bash
python manage.py migrate
```

4. 개발 서버 실행:
```bash
python manage.py runserver
```

5. 브라우저에서 `http://127.0.0.1:8000/` 접속

## 개발 환경 설정

개발 환경 설정을 위해 다음 패키지를 전역으로 설치하세요:
```bash
pip install uv
```

## 사용법

1. 회원가입 및 로그인
2. 새 아이트래킹 세션 생성
3. 시선 추적 시작
4. 결과 확인 및 히트맵 생성

## 라이센스

MIT 