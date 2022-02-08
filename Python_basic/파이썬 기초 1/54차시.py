# 'SyntaxError'는 오타나 문법 문제.
# 파이썬이 읽는걸 실패한 것
# '예외'는 문법은 괜찮은데 실행중 문제 발생, 프로그램 강종
# int랑 str을 잘못활용했거나 등등


# width = input("폭")
# height = input("높이")
# if width.isdigit() and height.isdigit():
## isdigit 함수는 숫자인지 여부를 판단해 T/F를 반환하는 함수
## if를 통해 예외처리하는 방식.
    # area = int(width) * int(height)
    # print(area)
# else:
#     print("숫자x")


# try~except~else~finally

# width = input("폭")
# height = input("높이")
# area = 0
# try:
#     area = int(width) * int(height)
#     print(area)
# except:      # try를 먼저 실행했는데 ValueError 같은게 뜰 경우
#     print("숫자가 아님")

# width = input("폭")
# height = input("높이")
# area = 0
# try:
#     area = int(width) * int(height)
# except:
#     print("숫자x")
# else:    # try에 오류가 없어서, except에서 걸리지 않았을 때 실행
#     print(area)
# finally:
#     print("finally 블록은 예외 발생여부 무관히 실행")

# width = input("폭")
# height = input("높이")
# area = 0
# try:
#     area = int(width) * int(height)
# except Exception as why: # Exception에 에러 내용이 저장, why로 참조
#     print(why); print(type(why))  # type(why)를 통해 ValueError 확인
# else:    # try에 오류가 없어서, except에서 걸리지 않았을 때 실행
#     print(area)
# finally:
#     print("finally 블록은 예외 발생여부 무관히 실행")


# width = input("폭")
# height = input("높이")
# area = 0
# try:
#     area = int(width) * int(height)
#     sci = int(width) / int(height)
# except ValueError as ve:   # Exception은 모든 에러 지칭
#     print("숫자x")          # 각각의 에러를 지칭할 수 있음
# except SyntaxError as se:  # except를 여러개 두고 각 에러마다
#     print("문법오류")       # 처리를 다르게 할 수 있음
# except ZeroDivisionError as ze:
#     print("0이 왜 분모에")
# else:    # try에 오류가 없어서, except에서 걸리지 않았을 때 실행
#     print(area)
#     print(sci)
# finally:
#     print("finally 블록은 예외 발생여부 무관히 실행")


# raise 문
# def calc_area(w,h):
#     if w.isdigit() and h.isdigit():
#         return int(w) * int(h)
#     else:
#         raise ValueError("숫자가 아님")  # else에 대해 강제로 예외상황 유발
# width = input("폭")
# height = input("높이")
# try:
#     area = calc_area(width, height) # calc_area 함수 작동시키고 그 결과를 area에 저장
# except ValueError as ve:        # V.E. 발생 시 이쪽으로.
#     print(ve)
# except Exception as ex:  # 그 외의 exception 들은 이쪽으로.
#     print(ex)
# else:
#     print(area)    # V.E. 에 대한 출력방식을 함수에 def 해놓고
# finally:              # try~except에서 V.E. 발생 시 함수 결과를 활용하는 구조 정도로 이해
#     print("항상출력")

#######발생하는 예외를 하나씩 함수에 지정해넣고, try~except~else에서는 해당 함수를 갖다쓰는 방식으로 코딩할 수 있다는 점.


# print(data_list[num])     # list 형식에 대해 []번째 자료값 호출
## 다양한 에러 발생 가능: Value(글자 입력), Index(10 입력)
# data_list = list(range(1,11))
# try:
#     num = int(input("인덱스입력"))
#     print(data_list[num])
# except IndexError as ie:
#     print(ie)
# except ValueError as ve:
#     print(ve)
# except Exception as ex:
#     print(ex)

data_list = list(range(1,11))
def listfn():
    try:
        num = int(input("인덱스입력"))
    except ValueError
try:
    num = int(input("인덱스입력"))
    print(data_list[num])
except IndexError as ie:
    print(ie)
except ValueError as ve:
    print(ve)
except Exception as ex:
    print(ex)