
http://www.ossrs.net/srs.release/releases/
且不讲为什么自己做服务器,因为喜欢研究.推荐docker安装srs,docker更容易集群和监控.更有强大的k8s

echo "Run SRS 3.0 in docker" &&
docker run -p 1935:1935 -p 1985:1985 -p 8080:8080 ossrs/srs:3

更有控制台监控,更强大,配合系统跟兼容,此项目会在github上越来越火.欢迎大家关注


docker run -p 1935:1935 -p 1985:1985 -p 8080:8080 \
    -v /path/of/yours.conf:/usr/local/srs/conf/srs.conf \
    -v /path/of/yours.log:/usr/local/srs/objs/srs.log \
    ossrs/srs:3
    
    
    阿里云
docker run registry.cn-hangzhou.aliyuncs.com/ossrs/srs:3


FFMPEG
docker run -p 1935:1935 -p 1985:1985 -p 8080:8080 \
    -v /path/of/ffmpeg:/usr/local/srs/objs/ffmpeg/bin/ffmpeg \
    ossrs/srs:3



RTMP: rtmp://127.0.0.1/live/livestream
FLV: http://127.0.0.1:8080/live/livestream.flv
HLS: http://127.0.0.1:8080/live/livestream.m3u8
