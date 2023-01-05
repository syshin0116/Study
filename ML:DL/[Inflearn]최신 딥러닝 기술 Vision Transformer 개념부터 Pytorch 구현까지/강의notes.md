---
layout: post
title: "[Pytorch]Vision Transformer notes"
date: 2022-12-30 09:50:23 +0900
category: Deep-Learning
---
# [Inflearn]최신 딥러닝 기술 Vision Transformer 개념부터 Pytorch 구현까지


## 강의소개
CNN - 합성곱 신경망: 20여년 전에 개발된 기술이지만 CNN을 능가하는 모델이 나오지 않았음

CNN을 사용하지 않으려는 이유: 필터를 움직이면서 보는 방법 -> 국소적인 부분만 관찰함 -> 이미지 전체를 고려하기 어려움 

attention 기법: 이미지 전체에서 어느부분을 주의깊게 봐야하는지 나타내는 기술, CNN과 attention을 복합적으로 사용하는 기술로 발달하다가 합성곱을 사용하지 않고 attention 만드로만 사용하는 기술로 발전

Vision Transformer가 2021년 9월 기준 1,2위를 다툼(3-7위는 CNN 최신기술인 efficientNet)

- 컴퓨터 비전(Computer vision)
- 합성곱 신경망(CNN)
- 비전 트랜스포터(Vision Transformer)

## 컴퓨터 비전(Computer Vision)

###1.1 비전 분야에서의 업무
1. Image Classification
2. Object Detection
3. Image Segmentation
4. Pose Estimatin

	- 사람의 관절을 나타내는 좌표를 예측
	
5.  Face Generation

### 1.2 연구 현황
CVPR, ECCV, ICCV, BMVC 등 학회 논문을 보는것이 가장 빠르다

###1.3 기업 현황
많은 사업에 Computer Vision 즉 CNN이 활용되고 있다
자율주행차의 선두기업인 Tesla는 CNN과 Transformer를 함께 사용하는 방법을 사용

### 1.4 테슬라의 비전 모델-HydraNet
- 2021년 8월 AI Day에서 공개
- Full Self-Driving에 사용됨
- 카메라 8대에서 데이터를 받음

자율주행차에서 눈 역할을 하는 3가지 장비
1. Camera
2. Radar
3. LIDAR

중 테슬라는 카메라만 사용

<img width="80%" alt="image" src="https://user-images.githubusercontent.com/99532836/210026343-bdc026ce-389d-4874-a38e-0d782d230c83.png" style="display:block; margin-left:auto; margin-right: auto;">

장점
1. 하나의 백보드를 사용하기 떄문에 효율적
2. Head마다 떼서 Tuning이 가능함
3. 멀티스킬 피쳐부분에서 별도 저장이 가능

왜 이미지 처리에 Transformer을 사용하였는가?
동일한 물체를 찍더라도 카메라 위치에 따라 다르게 보임
->이미지 스페이스에서 위치를 나타내는 포지셔널 임베디드 메트릭스를 사용

- CNNs + Transformer

## 합성곱 신경망(CNN)

### 2.1 합성곱 신경망
- AlexNet(2012)
- VGGNet(2014)
- ResNet(2015)
- DenseNet(2016)
- NasNet(2018)
- EfficientNet(2020)

EfficientNet: Rethinking Model Scaling for Convolutionla Neural Networks

<img width="80%" alt="image" src="https://user-images.githubusercontent.com/99532836/210166435-3ef32ad7-be57-4a29-9fdf-88e1202ba853.png" style="display:block; margin-left:auto; margin-right: auto;">

CNN을 배제하려는 이유
한번 연산시 국소적인 부분 기준으로 봄
깊은 신경망을 통해 node간의 관계를 볼 수 있음 (관계를 넓게 보려고 할 수록 layer가 많이 필요함)
국소적인 메커니즘이 전체를 봐야 할 때는 단점이 됨

## 어텐션 기법 - 키, 쿼리, 밸류는 무엇인가?
### 2.2 Attention
Attention 기법으로 CNN을 개선하는 방법

전체 픽셀에 대해서 각 픽셀에 대한 중요도를 곱하는 방식이 기본 (가중치)

#### 키, 쿼리, 밸류
- transformer또한 이 기반을 사용함 
- 파이썬 dictionary의 키 밸류와 유사함
- query : 데이터베이스 쿼리와 유사

<img width="80%" alt="image" src="https://user-images.githubusercontent.com/99532836/210167126-eb403cb8-2087-4379-b3c2-76ae44612a5a.png" style="display:block; margin-left:auto; margin-right: auto;">

Attention
- NLP 분야에서 활발히 쓰임
- BERT, GPT-3 모델이 대표적

### 비전 트랜스포머(Vit)

자연어 처리의 역사를 보면 여태 주축을 이룬 모델들은 lstm 기반 모델들이었다

lstm
- sequence 형태의 데이터를 받아 처리
-> 순서를 고려하여 처리
-> 하지만 단어들의 관계가 순차적이지 않기 때문에 한계가 있음
-> 초반 스텝에서 번역이 잘못되면 다음 번역에도 영향을 미친다는것이 단점

따라서 CNN, RNN등을 사용하지 않는 attention 기법이 주목을 받음

<img width="50%" alt="image" src="https://user-images.githubusercontent.com/99532836/210197245-3f854802-2116-49cf-8d7e-0b422285e65c.png" style="display:block; margin-left:auto; margin-right: auto;">

Input을 처리하는 Encoder와 Output을 처리하는 Decoder로 구성됨

ex) "I am a student" 의 문장이들어간다면
LSTM의 경우에는 각 단어를 Sequence로 나뉘어 개별로 들어가겠지만 Transformer의 경우 각 벡터들의 모음이 통째로 한꺼번에 매트릭스 형태로 들어감

Input Embedding을 통해 적절한 크기로 바꾸어줌

단어 수 만큼의 벡터들에서 -> 정해진 크기인 값들로 변환된 값 + Positional Encoding을 통해 위치값(가중치도 가능)

Multi-lead Attention: 학습에 따라서 Attention들이 같은 것을 보더라도 다른 관점에서 보기 위함

들어온 데이터의 크기와 내보내는 데이터의 크기가 같다 -> 여러 Layer 사용 가능(실제로  논문에서는 6번 과정을 반복함)