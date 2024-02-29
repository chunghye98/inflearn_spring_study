# 미니 프로젝트 1
## 만들고자 하는 시스템
간단한 출퇴근 사내 시스템, API 만 개발

### 사용할 기술 스택
- Java 17
- Spring Boot 3
- JPA
- MySQL

### 요구 사항
1. 팀 등록 기능
- 회사에 있는 팀을 등록할 수 있어야 한다.
- 필수 정보: `팀 이름`
2. 직원 등록 기능
- 직원을 등록할 수 있어야 한다.
- 필수 정보: `직원 이름` `팀의 매니저인지 아닌지 여부` `입사일` `생일`
3. 팀 조회 기능
- 모든 팀의 정보를 한 번에 조회 가능해야 한다.
  ```json
[
    {
        "name":"팀 이름",
        "manager":"팀 매니저 이름" // 없을 경우 null
        "memberCount" : 팀 인원 수 [숫자]
    }, ...
]
```
4. 직원 조회 기능
- 모든 직원의 정보를 한 번에 조회할 수 있어야 한다.
  ```json
[
    {
        "name" : "직원 이름",
        "teamName" : "소속 팀 이름",
        "role" : "MANAGER" or "MEMBER",
        "birthday : "1989-01-01",
        "workStartDate" : "2024-01-01"
    }, ...
]
```

## 프로젝트 개발
### 프로젝트 최초 설정
[start.spring.io](https://start.spring.io/)에서 스프링 프로젝트 시작한다.
![](https://velog.velcdn.com/images/chunghye98/post/2652e6eb-da7f-42e1-902a-6987ce6b9e54/image.png)

__Dependencies__
`Spring Data JPA` `MySQL Driver` `Spring Web`

Generate 하고 만들어진 폴더를 압축해제한다.

### DB 생성 및 연결
Database 관리 툴에서 `+` 버튼을 눌러 Datasource -> MySQL을 선택한다.
![](https://velog.velcdn.com/images/chunghye98/post/8db0fa64-0f3c-4984-8816-71ee68801477/image.png)
id와 password를 넣고 로컬에서 사용하는 MySQL의 포트를 넣어서 Test Connection을 한다.
Succeeded가 나오면 적용한다.

콘솔에 다음 명령어를 입력해서 DB를 생성한다.
```sql
create database company;
use company;
```

src > main > resource 에서 application.properties 파일의 이름을 application.yml 으로 변경한다(이 편이 depth로 분류되어서 다루기 편하다).
application.yml에서 다음 설정을 해서 프로젝트와 DB를 연결한다.
```yml
spring:
  datasource:
    url: jdbc:mysql://{주소}:{포트}/{DB 이름}
    username: {id}
    password: {password}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect
```

### 테이블 설계
필요한 테이블과 필드는 다음과 같다.

1. Team
    - id
    - name

2. Member
    - id
    - name
    - role
    - birthday
    - work_start_date

콘솔에 DDL로 테이블을 생성한다.
```sql
create table Team (
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);

create table Member (
    id bigint auto_increment,
    name varchar(255),
    role varchar(50),
    birthday Date,
    work_start_date Date,
    primary key (id),
    team_id bigint,
    foreign key (team_id) references Team (id)
);

show tables; // 생성한 테이블 확인
```

### Entity 매핑
__Team__
```java
@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();

	protected Team() {

	}

	public Team(String name) {
		this.name = name;
	}

	public void addMember(Member member) {
		this.members.add(member);
		member.setTeam(this);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Member> getMembers() {
		return members;
	}
}
```

__Member__
```java
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Role role;

	private LocalDate birthday;
	private LocalDate workStartDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	public Member() {
	}

	public Member(String name, boolean isManager, LocalDate birthday, LocalDate workStartDate) {
		this.name = name;
		this.role = Role.findRole(isManager);
		this.birthday = birthday;
		this.workStartDate = workStartDate;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public LocalDate getWorkStartDate() {
		return workStartDate;
	}

	public Team getTeam() {
		return team;
	}
}
```

