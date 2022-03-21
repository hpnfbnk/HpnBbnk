# HYPHEN BatchFirmBanking Module
하이픈-배치펌뱅킹서비스용 연동모듈입니다.

## 요구사항
JAVA 1.8 이상을 요구합니다.

## 설치
[JitPack](https://jitpack.io/#hpnfbnk/HpnBbnk) 을 통해 maven등으로 설치하실수 있습니다.

pom.xml에 아래 내용을 추가해주시면 됩니다.

```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.hpnfbnk</groupId>
    <artifactId>HpnBbnk</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Logging
SLF4J 를 사용하시고 log-level이 [debug] 이상이면 log가 기록됩니다.

[debug] level 시 : 주요 세부동작단계에서 logging

[trace] level 시 : 실제로 Hyphen server와 주고받는 전문까지 모두 logging

## 사용법

### 모듈 생성 및 초기화
```java
/**
 * 기본접속정보(Internet)를 사용하여 Instance 생성합니다.
 */
HpnBbnk hpnBbnk = new HpnBbnk();
```
```java
/**
* 전용선사용여부를 지정하여 Instance 생성합니다.
* @param delnYn 전용선사용여부 default=비사용
*/
HpnBbnk hpnBbnk = new HpnBbnk(true);
```

### 요청파일송신
```java
/**
* Hyphen으로 요청파일 송신
* @param sendCd 송신자코드 Hyphen에서 발급한 업체코드
* @param recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
* @param infoCd 파일종류구분코드 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
* @param filePath 송신대상파일 위치
* @param runMode 동작모드 Y:운영 T:test
* @return true:성공 false:실패
*/
boolean result = hpnBbnk.sendData("A001","0081", "R00", "./send.txt", "T");
```

### 수신목록조회
```java
/**
* 일반적인 조건으로 수신목록 조회 :
* 최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청
* @param finderCd 조회자코드 Hyphen에서 발급한 업체코드
* @param runMode 동작모드 Y:운영 T:test
* @return 수신목록
*/
List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A001", "T");
```
```java
/**
* 수신목록
* @param infoCd 파일종류구분코드 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
* @param sendCd 송신자코드
* @param recvCd 수신자코드
* @param seqNo 파일순번
* @param sendTm 송신시간
* @param recvTm 수신시간
* @param fileSize 파일크기
*/
public DtoSRList(String infoCd, String sendCd, String recvCd, String seqNo, String sendTm, String recvTm, long fileSize)
```

### 결과파일수신
```java
/**
* Hyphen에서 결과파일 수신
* @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
* @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
* @param infoCd 파일종류구분코드 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
* @param seqNo 파일순번
* @param sendDt 송신일자
* @param filePath 수신대상파일 저장위치
* @param runMode 동작모드 Y:운영 T:test
* @return true:성공 false:실패
*/
boolean result = hpnBbnk.recvData("0081", "A001", "R00", "001", "20220310", "./rcv.txt", "T");
```

* 보다 자세한 내용은 첨부된 [javadoc](https://hpnfbnk.github.io/HpnBbnk/javadoc/) 에 설명되어 있습니다.

## 예제
[HpnBbnkTest](https://github.com/hpnfbnk/HpnBbnk/blob/master/src/test/java/com/hyphen/fbnk/bbnk/HpnBbnkTest.java) 를 참고해 주세요.

## 기타참고자료
기타참고자료는 [/docs](https://github.com/hpnfbnk/HpnBbnk/tree/master/docs) 에 포함되 있습니다.
- [배치펌뱅킹-은행포맷.zip](https://hpnfbnk.github.io/HpnBbnk/배치펌뱅킹-은행포맷.zip) : 은행별 파일포맷
- [HYPHEN통합(v1004)_파일포맷.xlsx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN통합(v1004)_파일포맷.xlsx) : HYPHEN-공통 파일포맷(HYPHEN 타입)
- [HYPHEN통합(금결원호환v2001)_파일포맷.docx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN통합(금결원호환v2001)_파일포맷.docx) : HYPHEN-공통 파일포맷(금결원호환 타입)
- [출금이체정보_통합관리시스템_전산설계서(KSNET)V3.7.docx](https://hpnfbnk.github.io/HpnBbnk/출금이체정보_통합관리시스템_전산설계서(KSNET)V3.7.docx) : 계좌통합관리업무용 안내문서
- [HYPHEN배치대행_파일포맷(금결원호환v2016).xls](https://hpnfbnk.github.io/HpnBbnk/HYPHEN배치대행_파일포맷(금결원호환v2016).xls) : 배치대행이체 파일포맷
- [법인카드가맹점신표준전문(banking).xls](https://hpnfbnk.github.io/HpnBbnk/법인카드가맹점신표준전문(banking).xls) : 법인카드사용내역 파일포맷
- [HYPHEN펌뱅킹_파일종류구분코드표.xlsx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN펌뱅킹_파일종류구분코드표.xlsx) : 파일종류구분표
- [배치펌뱅킹_오류코드정리.xlsx](https://hpnfbnk.github.io/HpnBbnk/배치펌뱅킹_오류코드정리.xlsx) : 오류코드표
- [HYPHEN_Tcp송수신전문내역서.xls](https://hpnfbnk.github.io/HpnBbnk/HYPHEN_Tcp송수신전문내역서.xls) : 전문상세내역서
