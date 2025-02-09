## BaseDaysAgoMemoFetcher (추상 클래스)
각 클래스에서 날짜만 다르고 나머지 로직은 동일하니까, 공통 로직을 BaseDaysAgoMemoFetcher에서 처리하고,
각 MemoFetcher 구현체는 몇 일 전인지(daysAgo)만 지정한다.

### YesterdayMemoFetcher
1일 전 커밋들의 메모들을 가져온다.
### ThreeDaysAgoMemoFetcher
3일 전 커밋들의 메모들을 가져온다.
### SevenDaysAgoMemoFetcher
7일 전 커밋들의 메모들을 가져온다.

## 장점
- 클래스 이름 유지
  - n 일 전이라는 명시적인 클래스 그대로 유지
- 중복 최소화
  - 날짜 계산 및 API 호출 로직은 `BaseDaysAgoMemoFetcher`에서 공통 처리
- 확장성 증가
  - 필요하면 `FourteenDaysAgoMemoFetcher` 같은 클래스도 쉽게 추가 가능

- OCP 원칙을 잘 지킴
  - 기존 클래스를 수정하지 않고 새로운 기능을 추가할 수 있음
  - 각 구체 클래스는 BaseDaysAgoMemoFetcher 의 생성자에 날짜 차이만 다르게 전달하면서 기존 클래스를 수정하지 않고 새로운 클래스만 추가하는 방식
