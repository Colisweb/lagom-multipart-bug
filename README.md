# lagom-multipart-bug

## What is the bug ?

When you do multiple multipart/form-data requests, at some point one will lock and will never end.
The HTTP client will wait until its timeout, if one.

## How to reproduce the bug ?

launch the project:
```
$ sbt runAll
```

With a HTTP client, do multiple HTTP multipart/form-data reqest on `http://localhost:9000/api/files`.
Here is an example with `curl`:
```
$ curl -v -H "Expect:" -F files=@/path/to/a/file http://localhost:9000/api/files 
```

**Remarks:**   
- The `-H "Expect:"` in the `curl` example come from here: http://www.iandennismiller.com/posts/curl-http1-1-100-continue-and-multipartform-data-post.html and is required with `curl` only (Postman doesn't need this for example)

- In the `project/build.properties` files, you'll see that I used snapshots version (`1.3.7-SNAPSHOT` and/or `1.4.0-SNAPSHOT`) of Lagom.
You'll need this too if you want to benefit from the custom logguer configuration (uselful for debuging).
More information, see: https://github.com/lagom/lagom/pull/889
