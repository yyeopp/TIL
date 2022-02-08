#b 는 소문자 입니다.

alphabet = str(input())
if alphabet.isupper():
    print("{} 는 대문자 입니다.".format(alphabet))
else:
    print("{} 는 소문자 입니다.".format(alphabet))