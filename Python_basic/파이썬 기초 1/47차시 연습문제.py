#가변형인자로정수들을입력받아곱을반환하는함수를정의하고,단, 1, 2, '4', 3와같이제대로입력되지않은경우예외를처리하는프로그램을작성하십시오.
# 출력은 에러발생


def calc(*params):
    params = list(int(input().split(',')))
    total = 1
    try:
        for i in params:
            total *= i
    except ValueError as ve:
        print("에러발생")
    else:
        print(total)
calc()