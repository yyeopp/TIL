def 소수판독기():
    x = int(input())
    y = 1
    약수total = 0
    while y <= x:
        if x % y == 0:
            약수total +=1
        y +=1
    if 약수total == 2:
        print("소수입니다")
    else:
        print("소수가 아닙니다")
소수판독기()