# # score = 80
# # if score >= 60:
# #     print("%d 점" % score)
# #     print("합격입니다.")
# #
# # result = "불합격입니다"
# # score = 80
# # if score >= 60:
# #     result = "합격입니다"
# # print(result)
# #
# score = 80
# if score >= 60:
#     print("합격입니다")
# else:
#     print("불합격입니다")
#
# score = int(input("점수를 입력하세요:"))
# if score >= 90:
#     grade = "A"
# elif 80<=score<90:
#     grade = "B"
# elif 70<=score<80:
#     grade = "C"
# elif 60<=score<70:
#     grade = "D"
# else:
#     grade = "F"
# print("%d점 %s등급입니다" %(score, grade))

operand1, operator, operand2 = 0, "", 0
operand1 = int(input("첫 번째 숫자를 입력하세요: "))
operator = input("연산자를 입력하세요 (+, -, *, /): ")
operand2 = int(input("두 번째 숫자를 입력하세요: "))

if operator == "+":
    print("{0} + {1} = {2}".format(operand1, operand2, operand1+operand2))
elif operator == "-":
    print("{0} - {1} = {2}".format(operand1, operand2, operand1-operand2))
elif operator == "*":
    print("{0} * {1} = {2}".format(operand1, operand2, operand1*operand2))
elif operator == "/":
    print("{0} / {1} = {2:.2f}".format(operand1, operand2, operand1/operand2))
else:
    print("%s는 지원하지 않는 연산자입니다."%operator)
