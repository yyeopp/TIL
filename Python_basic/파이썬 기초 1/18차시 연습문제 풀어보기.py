#1~200 사이의 정수 가운데 7의 배수이면서 5의 배수는 아닌 모든 숫자들을 찾아 콤마(,)로 구분된 문자열을 구성해 출력하는 프로그램을 작성하십시오.
operand1 = 1
answer = {int()}

while operand1 <= 200:
    if operand1 % 7 == 0:
        answer |= {int(operand1)}
        operand1 += 1
    else:
        operand1 += 1
print(answer)