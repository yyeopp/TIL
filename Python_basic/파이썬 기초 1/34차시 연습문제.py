word = input("")
reverse = ""
for a in word:
    reverse = a + reverse
print(reverse)
if reverse == word:
    print("입력하신 단어는 회문(Palindrome)입니다.")
else:
    print("입력하신 단어는 회문(Palindrome)이 아닙니다.")