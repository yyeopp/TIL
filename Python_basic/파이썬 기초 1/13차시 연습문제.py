#다음의 결과와 같이 임의의 양의 정수를 입력받아 그 정수의 모든 약수를 구하십시오
# 1은(는) 9의 약수입니다.

operand1 = int(input())
operand2 = 1
while operand2 <= operand1:
    if operand1 % operand2 == 0:
        print("{0}은(는) {1}의 약수입니다.".format(operand2, operand1))
        operand2 += 1
    else: operand2 +=1
#
# operand1 = int(input())
# operand2 = 1
# if operand1 % operand2 == 0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2, operand1))
# else: operand2 +=1
# if operand1 % operand2 == 0:
#     print
#
# if operand1 % operand2 ==0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2+1, operand1))
# elif operand1 % (operand2 + 1) == 0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2 + 1, operand1))
# elif operand1 % (operand2+1) ==0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2+1, operand1))
# elif operand1 % (operand2+1) ==0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2+1, operand1))
# elif operand1 % (operand2 + 1) == 0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2 + 1, operand1))
# elif operand1 % (operand2+1) ==0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2+1, operand1))
# elif operand1 % (operand2+1) ==0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2+1, operand1))
# elif operand1 % (operand2+1) ==0:
#     print("{0}은(는) {1}의 약수입니다".format(operand2+1, operand1))

