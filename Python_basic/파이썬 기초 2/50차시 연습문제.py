#Person를부모클래스로Male, Female자식클래스를정의하는코드를작성하십시오."Unknown"을반환하는Person클래스의getGender메서드를
# Male클래스와Female클래스는 "Male", "Female"값을반환하는메서드로오버라이딩합니다.
#Male
#Female

class Person:
    def getGender(self):
        return "Unknown"
class Male(Person):
    def getGender(self):
        return "Male"
class Female(Person):
    def getGender(self):
        return "Female"
M = Male(); F = Female()
print(M.getGender())
print(F.getGender())

