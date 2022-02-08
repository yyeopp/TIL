# 2019년 기준 홍길동 20세 입력 시 2099년에 100세가 된다는 출력
# 홍길동(은)는 2099년에 100세가 될 것입니다.

name = str(input("이름을 입력하시오: "))
age = int(input("현재 나이를 입력하시오: "))
def agecount():
    global age
    future = 100-age+2019
    return future
result = agecount()
print("{0}(은)는 {1}년에 100세가 될 것입니다.".format(name, result))