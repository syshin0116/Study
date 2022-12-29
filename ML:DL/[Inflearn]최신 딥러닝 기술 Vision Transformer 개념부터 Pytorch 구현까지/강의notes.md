# [Inflearn]최신 딥러닝 기술 Vision Transformer 개념부터 Pytorch 구현까지


## 강의소개
CNN - 합성곱 신경망: 20여년 전에 개발된 기술이지만 CNN을 능가하는 모델이 나오지 않았음

CNN을 사용하지 않으려는 이유: 필터를 움직이면서 보는 방법 -> 국소적인 부분만 관찰함 -> 이미지 전체를 고려하기 어려움 

attention 기법: 이미지 전체에서 어느부분을 주의깊게 봐야하는지 나타내는 기술, CNN과 attention을 복합적으로 사용하는 기술로 발달하다가 합성곱을 사용하지 않고 attention 만드로만 사용하는 기술로 발전

Vision Transformer가 2021년 9월 기준 1,2위를 다툼(3-7위는 CNN 최신기술인 efficientNet)

- 컴퓨터 비전(Computer vision)
- 합성곱 신경망(CNN)
- 비전 트랜스포터(Vision Transformer)

### 컴퓨터 비전(Computer Vision)

####1.1 비전 분야에서의 업무
1. Image Classification
2. Object Detection
3. Image Segmentation
4. Pose Estimatin

	- 사람의 관절을 나타내는 좌표를 예측
	
5.  Face Generation

####1.2 연구 현황
CVPR, ECCV, ICCV, BMVC 등 학회 논문을 보는것이 가장 빠르다

####1.3 기업 현황
많은 사업에 Computer Vision 즉 CNN이 활용되고 있다
자율주행차의 선두기업인 Tesla는 CNN과 Transformer를 함께 사용하는 방법을 사용

#### 1.4 테슬라의 비전 모델-HydraNet
- 2021년 8월 AI Day에서 공개
- 