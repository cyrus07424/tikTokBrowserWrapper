# tikTokBrowserWrapper
Unofficial TikTok Browser 2.0 wrapper for Java

## Usage

### Get user info

```java
TikTokBrowserWrapper tikTokBrowserWrapper = new TikTokBrowserWrapper(PHPSESSID, ses);
JsonUsrInfo jsonUsrInfo = tikTokBrowserWrapper.getJsonUsrInfo(USER_ID);
```

### Get user's video list

```java
TikTokBrowserWrapper tikTokBrowserWrapper = new TikTokBrowserWrapper(PHPSESSID, ses);
JsonPst jsonPst = tikTokBrowserWrapper.getJsonPst(USER_ID);
```