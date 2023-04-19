i,k=0,0 #i:세로 줄,  k:가로 칸
while i<9: #총 8줄
    if i<5: #첫 5줄 i=0,1,2,3,4
        k=0
        while k<4-i: #가로 4-i만큼 빈칸 출력  #i=0, k=0,1,2,3, i=1, k=0,1,2
            print(' ',end='')
            k+=1
        k=0
        while k<i*2+1: #가로 2i+1만큼 별 출력 #i=0, k=0 #i=1, k=0,1,2
            print('\u2605',end='')
            k+=1
    else: #위 과정 거꾸로 진행 
        k=0
        while k<i-4:
            print(' ',end='')
            k+=1
        k=0
        while k<(9-i)*2-1:
            print('\u2605',end='')
            k+=1
    print()
    i+=1
