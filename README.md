# HYPHEN BatchFirmBanking Module
하이픈-배치펌뱅킹서비스용 연동모듈입니다.

## 요구사항
JAVA 1.7 이상을 요구합니다.

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
    <version>1.9.0</version>
</dependency>
```

## HYPHEN 서버 주소
인터넷존 - 운영 : ip 121.138.30.10 / port 29994

인터넷존 - 개발 : ip 121.138.30.31 / port 29994

전용선존 - 운영 : ip 129.200.9.11 / port 50001

전용선존 - 개발 : ip 129.200.9.18 / port 50001

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

### 요청파일 송신
```java
/**
* Hyphen으로 요청파일 송신
* @param sendCd 송신자코드 Hyphen에서 발급한 업체코드
* @param recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
* @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I02), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
*              배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3) 등..
* @param filePath 송신대상파일 위치
* @param runMode 동작모드 Y:운영 T:test
* @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용) 
* @return true:성공 false:실패
*/
boolean result = hpnBbnk.sendData("A001","0081", "R00", "./send.txt", "T", "");
```

### 수신목록 조회
```java
/**
* 일반적인 조건으로 수신목록 조회 :
* 최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청
* @param finderCd 조회자코드 Hyphen에서 발급한 업체코드
* @param runMode 동작모드 Y:운영 T:test
* @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return 수신목록
*/
List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A001", "T", "");
```
```java
/**
* 수신목록
* @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
*              해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등.. 
* @param sendCd 송신자코드
* @param recvCd 수신자코드
* @param seqNo 파일순번
* @param sendTm 송신시간
* @param recvTm 수신시간
* @param fileSize 파일크기
*/
public DtoSRList(String infoCd, String sendCd, String recvCd, String seqNo, String sendTm, String recvTm, long fileSize)
```

### 결과파일 수신
```java
/**
* Hyphen에서 결과파일 수신
* @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
* @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
* @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
*              해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등.. 
* @param seqNo 파일순번
* @param sendDt 송신일자
* @param filePath 수신대상파일 저장위치
* @param runMode 동작모드 Y:운영 T:test
* @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return true:성공 false:실패
*/
boolean result = hpnBbnk.recvData("0081", "A001", "R00", "001", "20220310", "./rcv.txt", "T", "");
```

### 여러개파일 송신
```java
/**
* Hyphen으로 여러개의 파일 송신
* @param sendCd 송신자코드 Hyphen에서 발급한 업체코드
* @param sendLists 송신파일 목록
* @param sFNmTp 파일명타입 KEDU:k-edufine타입 등..
* @param runMode 동작모드 Y:운영 T:test
* @return 송신파일 처리결과 목록
*/
List<DtoFileList> resultLists = hpnBbnk.sendDataMulti("A001", sendDataLists, "", "T");
```
```java
/**
 * 송수신파일목록
 * @param sendDt 송수신일자
 * @param infoCd 파일종류구분코드
 * @param sendCd 송신자코드
 * @param recvCd 수신자코드
 * @param seqNo 순번
 * @param filePath 저장파일경로
 * @param retYn 송수신결과
 * @param sendCdPwd 송신요청시 송신자 통신비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
*/
public DtoFileList(String sendDt, String infoCd, String sendCd, String recvCd, String seqNo, String filePath, boolean retYn, String sendCdPwd)
```

### 여러개파일 수신
```java
/**
* Hyphen에서 여러개 파일 수신
* @param finderCd 조회자코드
* @param targetCd 조회대상자코드 모든대상자:9999)
* @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
* @param fromDt 조회범위-시작일자 YYYYMMDD
* @param toDt 조회범위-종료일자 YYYYMMDD
* @param findRng 조회범위-수신여부 미수신건만:E 모두:A
* @param sFNmTp 파일명타입 KSNET타입:"", K-edufine타입:KEDU
* @param recvDir 수신파일저장 디렉토리
* @param runMode 동작모드 Y:운영 T:test
* @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return 수신파일목록
*/
List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("A001", "9999", "ZZZ", "20220315", "20220315", "A", "", "./sample", "T", "");
```

### 파일 수신하여 DB에 insert (법인카드사용내역에 한해..)
```java
/**
* Hyphen에서 결과파일 수신하여 DB에 insert (법인카드사용내역에 한하여..)
* @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
* @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
* @param infoCd 파일종류구분코드 C01:법인카드-승인내역, C02:법인카드-매입, C03:법인카드-청구, C04:법인카드-카드기본정보, C05:법인카드-결재정보, C06:법인카드-한도정보
* @param seqNo 파일순번
* @param sendDt 송신일자
* @param filePath 수신대상파일 저장위치
* @param runMode 동작모드 Y:운영 T:test
* @param dbDriver JDBC Driver
* @param dbUrl JDBC Url
* @param dbUser DB user id
* @param dbPass DB user password
* @param senderPwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return true:성공 false:실패
*/
boolean result = hpnBbnk.recvData2DB(sendCd, recvCd, infoCd, seqNo, sendDt, filePath, runMode, dbDriver, dbUrl, dbUser, dbPass, senderPwd);
```

### 여러개 파일 수신하여 DB에 insert (법인카드사용내역에 한해..)
```java
/**
* Hyphen에서 여러개 파일 수신하여 DB에 insert (법인카드사용내역에 한하여..)
* @param finderCd 조회자코드
* @param targetCd 조회대상자코드 모든대상자:9999)
* @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
* @param fromDt 조회범위-시작일자 YYYYMMDD
* @param toDt 조회범위-종료일자 YYYYMMDD
* @param findRng 조회범위-수신여부 미수신건만:E 모두:A
* @param sFNmTp 파일명타입 KSNET타입:"", K-edufine타입:KEDU
* @param recvDir 수신파일저장 디렉토리
* @param runMode 동작모드 Y:운영 T:test
* @param dbDriver JDBC Driver
* @param dbUrl JDBC Url
* @param dbUser DB user id
* @param dbPass DB user password
* @param senderPwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return 수신처리결과목록
*/
List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti2DB(recvCd, sendCd, infoCd, sendDt, sendDt, "E", "", "./sample", runMode, dbDriver, dbUrl, dbUser, dbPass, senderPwd);
```

### Dto 리스트를 받아 요청파일로 생성
```java
/**
* Dto 리스트를 받아 요청파일로 생성
* @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I02), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
*               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3) 
* @param dtoList Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
*               증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y06, IY6 용), 계좌변경접수결과:DtoShift(infoCd:Y01, IY1 용)
* @param desFilePath 요청파일저장경로
* @return true:성공 false:실패
*/
List<DtoBill> dtoBillList = new ArrayList<>();
dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
    "003", "12345678901234", 1500L, "", "", 0L, "NTP0001", "", "당근10kg", "", "당근농장A"));
dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
    "004", "123456789004", 1500L, "", "", 0L,"NTP0002", "", "당근102kg사주세요", "", "당근농장B"));
boolean result = hpnBbnk.makeDataFile("200", dtoBillList, "./sample/make200.txt");
```

### Dto 리스트를 받아 요청파일로 생성하여 HYPHEN으로 송신
```java
/**
* Dto 리스트를 받아 요청파일로 생성하여 HYPHEN으로 송신
* @param sendCd sendCd 송신자코드 Hyphen에서 발급한 업체코드
* @param recvCd recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
* @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I03), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
*               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3) 
* @param dtoList Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
*               증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y06, IY6 용), 계좌변경접수결과:DtoShift(infoCd:Y01, IY1 용)
* @param saveDir 생성돤파일 저장할 디렉토리
* @param runMode 동작모드 Y:운영 T:test
* @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return true:성공 false:실패
*/
List<DtoBill> dtoBillList = new ArrayList<>();
dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
    "003", "12345678901234", 1500L, "", "", 0L, "NTP0001", "", "당근10kg", "", "당근농장A"));
dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
    "004", "123456789004", 1500L, "", "", 0L, "NTP0002", "", "당근102kg사주세요", "", "당근농장B"));
boolean result = hpnBbnk.sendDataDto("A004", "0084", "200", dtoBillList, "./sample", "T", "");
```

### 결과파일을 Dto 리스트로 변환
```java
/**
* 결과파일을 Dto 리스트로 변환
* @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I03), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
 *              해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02) , 배치성실시간-송금이체(BR3)
* @param srcFilePath 결과파일위치
* @return Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
*                  증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y05, IY5, Y06, IY6 용),  해지통보:DtoCanc(Y03, IY3 용)
*                  계좌변경접수/처리결과:DtoShift(infoCd:Y01, IY1, Y02, IY2 용)
*/
List<DtoBill> dtoBillList = (List<DtoBill>) hpnBbnk.makeDtoList("200", "./sample/200ComRecv.txt");
```

### 결과파일 수신하여 Dto 리스트로 변환
```java
/**
* Hyphen에서 결과파일 수신하여 Dto 리스트로 변환
* @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
* @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
* @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I03), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
 *              해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 
* @param seqNo 파일순번
* @param sendDt 송신일자
* @param saveDir 수신파일보관경로
* @param runMode runMode 동작모드 Y:운영 T:test
* @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
* @return Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
*                  증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y05, IY5, Y06, IY6 용),  해지통보:DtoCanc(Y03, IY3 용)
*                  계좌변경접수/처리결과:DtoShift(infoCd:Y01, IY1, Y02, IY2 용)
*/
List<DtoPay> dtoPayList = (List<DtoPay>) hpnBbnk.recvDataDto("0081", "A004", "300", "001", "20220701", "./sample", "T", "");
```

* 보다 자세한 내용은 첨부된 [javadoc](https://hpnfbnk.github.io/HpnBbnk/javadoc/) 에 설명되어 있습니다.

## 예제
[HpnBbnkTest](https://github.com/hpnfbnk/HpnBbnk/blob/master/src/test/java/com/hyphen/fbnk/bbnk/HpnBbnkTest.java) 를 참고해 주세요.

## 기타참고자료
기타참고자료는 [/docs](https://github.com/hpnfbnk/HpnBbnk/tree/master/docs) 에 포함되 있습니다.
- [HYPHEN 배치펌뱅킹서비스용 연동Demon모듈](https://hpnfbnk.github.io/HpnBbnkDemon/HpnBbnkDemon-1.3.0.zip) : 주기적으로 반복 동작해 송수신을 자동으로 하는 독립적인 데몬형태모듈입니다.
- [HYPHEN 통합(v1004)_파일포맷.xlsx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN통합(v1004)_파일포맷.xlsx) : HYPHEN-공통 파일포맷(HYPHEN 타입)
- [HYPHEN 통합(금결원호환v2001)_파일포맷.docx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN통합(금결원호환v2001)_파일포맷.docx) : HYPHEN-공통 파일포맷(금결원호환 타입)
- [HYPHEN 배치대행_운영설명서(v3.2.1).docx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN배치대행_운영설명서(v3.2.1).docx) : 배치대행 운영설명
- [HYPHEN 배치대행_파일포맷(금결원호환)(v3.3.0).xlsx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN배치대행_파일포맷(금결원호환)(v3.3.0).xlsx) : 배치대행이체 파일포맷
- [HYPHEN펌뱅킹_파일종류구분코드표.xlsx](https://hpnfbnk.github.io/HpnBbnk/HYPHEN펌뱅킹_파일종류구분코드표.xlsx) : 파일종류구분표
- [법인카드가맹점신표준전문(banking).xls](https://hpnfbnk.github.io/HpnBbnk/법인카드가맹점신표준전문(banking).xls) : 법인카드사용내역 파일포맷
- [법인카드가맹점신표준전문(확장포맷-1.5.0).xls](https://hpnfbnk.github.io/HpnBbnk/법인카드가맹점신표준전문(확장포맷-1.5.0).xls) : 법인카드사용내역 파일포맷(확장) 
- [법인카드사용내역-샘플.zip](https://hpnfbnk.github.io/HpnBbnk/법인카드사용내역-샘플.zip) : 법인카드사용내역 샘플 data file
- [배치펌뱅킹_오류코드정리.xlsx](https://hpnfbnk.github.io/HpnBbnk/배치펌뱅킹_오류코드정리.xlsx) : 오류코드표
- [HYPHEN_Tcp송수신전문내역서.xls](https://hpnfbnk.github.io/HpnBbnk/HYPHEN_Tcp송수신전문내역서.xls) : 전문상세내역서
- [HYPHEN 배치성-실시간 파일포맷](https://hpnfbnk.github.io/HpnBbnk/HYPHEN배치성-실시간_파일포맷.xlsx) : 배치성-실시간업무용(송금대행 등..) 파일포맷
- [배치펌뱅킹-은행포맷.zip](https://hpnfbnk.github.io/HpnBbnk/배치펌뱅킹-은행포맷.zip) : 은행별 파일포맷
- [출금이체정보_통합관리시스템_전산설계서(HYPHEN)V3.8.pdf](https://hpnfbnk.github.io/HpnBbnk/출금이체정보_통합관리시스템_전산설계서(HYPHEN)V3.8.pdf) : 금결원 PAYINFO 업무용 안내문서
- [출금이체정보_통합관리시스템_전산설계서(HYPHEN-배치대행업체용)V3.3.docx](https://hpnfbnk.github.io/HpnBbnk/출금이체정보_통합관리시스템_전산설계서(HYPHEN-배치대행업체용)V3.3.docx) : 금결원 PAYINFO 업무용 안내문서(배치대행업체용) 


