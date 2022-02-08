# 1부터 100사이의 숫자 중 3의 배수의 총합을 for 문을 이용해 출력하십시오.
#1부터 100사이의 숫자 중 3의 배수의 총합: 1683
total = 0
for n in range(1,101,1):
    if n % 3 != 0:
        continue
    total += n
print("1부터 100사이의 숫자 중 3의 배수의 총합: {}".format(total))