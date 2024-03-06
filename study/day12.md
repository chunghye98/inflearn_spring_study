## 리눅스 명령어 다뤄보기
### EC2 접속
맥이라면 400으로 pem 키 권한 설정해야 함
> chmod : 파일이나 폴더의 권한을 변경

ssh -i 경로/키페어이름.pem ec2-user@IP

> 아마존 리눅스의 이름은 ec2-user

chmod : 파일이나 폴더의 권한을 변경

### 자주 사용하는 명령어
`mkdir` 폴더를 만드는 명령어 

`rm` 파일을 삭제하는 명령어

`cd` 폴더 안으로 들어가는 명령어, change directory

`pwd:` 현재 위치를 확인하는 명령어, print working directory

`cd ..`  상위 폴더로 올라가는 명령어

`rmdir` 이어있는 폴더(디렉토리)를 제거하는 명령어

`ls` 현재 위치에서 폴더나 파일을 확인하는 명령어

`ls -l`  상세한 정보도 같이 확인 가능
   
   drwxr-xr-x. 2 ec2-user ec2-user 6 Mar  6 10:28 folder1

```
d: 디렉토리
r: 읽을 수 있는 권한
w: 쓸 수 있는 권한
x: 실행할 수 있는 권한
폴더소유자의 권한/폴더소유그룹의 권한/아무나 접근했을 때의 권한
2: 폴더에 걸려 있는 바로가기의 개수
ec2-user: 이 폴더의 소유 그룹 이름
6: 이 폴더(파일)의 크기, byte 단위
Mar  6 10:28 : 파일의 최종 변경 시각
```



## 배포를 위한 프로그램 설치하기
1. 코드를 가져오기 위한 git
2. 우리가 만든 서버를 구동할 java
3. 데이터베이스의 역할을 할 mysql

```
sudo yum update
```
- sudo: 관리자 모드로 실행
- yum: 리눅스 패키지 프로그램
- update: 현재 설치된 프로그램들을 최신화한다.

### yum을 이용해 git을 다운로드 
```
sudo yum install git -y
```
- -y : yes 누르는거 무시함

### 자바 설치
```
sudo yum install java-11-amazon-corretto -y
```

### 자바 버전 확인
```
java -version
```

### mysql 설치
```
sudo dnf install https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm
```
```
sudo dnf install mysql-community-server
```

__오류 발생__
```
The GPG keys listed for the "MySQL 8.0 Community Server" repository are already installed but they are not correct for this package.
```

__해결__
```
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
sudo yum update
두 명령어 실행 후 재설치
sudo dnf install mysql-community-server
```

아마존 리눅스 2023에서 MySQL 설치 중 발생하는 GPG 오류를 해결하기 위해서는 기존에 설치된 GPG Key를 갱신하고 패키지를 업데이트 하는 절차가 필요할 수 있다.

### mysql 상태 확인
```
sudo systemctl status mysqld
```

### mysql 재시작
```
sudo systemctl restart mysqld
```
active 상태로 변경됨

### mysql8의 임시 비밀번호를 확인하는 명령어
```
sudo cat /var/log/mysqld.log | grep "A temporary password"
```

### mysql 접속
```
mysql -u root -p
```

### 임시 비밀번호 변경
```
alter user 'root'@'localhost'
identified with mysql_native_password by '비밀번호';
```
8자리 이상, 대문자 소문자 특수문자 포함

## Swap 메모리

현재 사용하는 컴퓨터는 사양이 낮다. swap 설정을 하자. swap 메모리는 메모리가 부족한 경우 일부 디스크를 사용할 수 있도록 해준다.

### swap 메모리를 할당한다 (128M * 16 = 2GB)
```
sudo dd if=/dev/zero of=/swapfile bs=128M count=16
```
### 스왑 파일에 대한 권한 업데이트
```
sudo chmod 600 /swapfile
```
### swap 영역 설정
```
sudo mkswap /swapfile
```
### swap 파일을 사용할 수 있도록 만든다.
```
sudo swapon /swapfile
```
### swap 성공 확인
```
sudo swapon -s
```

## 프로그램 배포하기
### 빌드
```
# 권한 부여
chmod +x ./gradlew
# test 없이 빌드
./gradlew build -x test
```
### 빌드된 프로젝트 실행
```
java -jar build/libs/library-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
Spring boot에는 톰캣이 내장되어 있어서 jar 파일을 실행해도 서버가 올라간다.

### 프로그램 중단하기
```
ctrl + c
```

### 현재 빌드된 프로그램을 제거
```
./gradlew clean
```

## 종료되지 않는 실행
- foreground: 우리가 보고 있는 프로그램
- background: 우리가 보고 있지 않은데 실행 중인 프로그램

### background로 동작하게 만드는 명령어
```
nohup [명령어] &
nohup java -jar build/libs/library-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev &
```


### background 서버 동작 중지 방법
```
# 현재 실행 중인 프로그램 목록 확인
ps aux 

# 실행 중인 프로그램 목록 중 java 가 들어가는 프로그램
ps aux | grep java 

# 프로그램 중지
kill -9 프로그램번호
```


### 파일을 확인하는 명령어
```
vi nohup.out
```
- vi: 리눅스 편집기인 vim을 이용해서 파일을 연다

```
cat nohup.out
```
- cat : 파일에 있는 내용물을 모두 출력하는 명령어

```
# 현재 파일의 끝 부분을 출력하는 명령어
tail nohup.out

# 실시간으로 파일의 끝 부분을 출력한다.
tail -f nohup.out
```




