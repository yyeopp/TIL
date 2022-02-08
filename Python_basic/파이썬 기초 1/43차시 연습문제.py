# 인자로전달된숫자를이용해카운트다운하는함수countdown을정의하고,이함수를이용하여countdown(0), countdown(10)을순서대로실행하십시오.
# 0보다작거나같은인자가전달되었을경우"카운트다운을 하려면 0보다 큰 입력이 필요합니다."를출력하십시오.

start = int(input())
def countdown(start):
    if start >= 1:
        while start >= 1:
            print(start)
            start -= 1
    else:
        raise ValueError("카운트다운을 하려면 0보다 큰 입력이 필요합니다.")
try:
    countdown(start)
except ValueError as ve:
    print(ve)