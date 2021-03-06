# Quartz配置
---
### Quartz的配置格式如下：

[秒] [分钟] [小时] [日] [月] [星期] [年]

位置|字段|范围|符号|
--|--|--|--
1|秒|0-59|, - * /
2|分钟|0-59|, - * /
3|小时|0-23|, - * /
4|日|1-31|, - * ? / L W C
5|月|1-12|, - * /
6|星期|1-7|, - * ? / L C #
7|年（选填）|空或1970-2099|, - * /

### 符号意义：

* *：表示所有值，比如分位置设置，表示每一分钟都会触发。
* ?：表示不指定值，只用在日和星期，表示不需要关心当前这个字段。比如：要在每月的10号触发一个操作，但不需要关心是周几，需要给星期那个字段设置为?，具体设置为：0 0 0 10 * ?
* -：表示区间，比如小时上设置10-12，表示10,11,12点都会触发。
* ,：表示指定多个值，例如在星期字段上设置MON,WED,FRI表示周一，周三，周五触发。
* /：表示递增触发，比如秒上设置5/15，表示从5秒开始，每15秒触发（5，20，35，50）。
* L：表示最后的意思，只用在日或星期上，用在日字段上，表示当月的最后一天，在星期字段上，表示周六，相当于7或SAT，如果在L前加上数字，表示该数据的最后一个，比如在星期字段上写6L,表示本月最后一个星期五。
* W：只能用在日字段上，表示离指定日期的最近的那个工作日（周一至周五），
  * 例如在日字段上设置"15W"，表示离每月15号最近的那个工作日触发。
    * 如果15号正好是周六，则找最近的周五(14号)触发, 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。
  * 如果指定格式为 "1W",它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注，"W"前只能设置具体的数字,不允许区间"-").
  * 'L'和 'W'可以一组合使用。如果在日字段上设置"LW",则表示在本月的最后一个工作日触发(一般指发工资 )
* #： 只用在星期上，表示本月的第几个周。
  * 例如在周字段上设置"6#3"表示在每月的第三个周六
  * #5表示本月第五个周

### 常用示例
|配置|意义|
|--|--|
|0 0 12 * * ?|每天12点触发|
|0 15 10 ? * *|每天10点15分触发|
|0 15 10 * * ?|每天10点15分触发|
|0 15 10 * * ? *|每天10点15分触发|
|0 15 10 * * ? 2005|2005年的每天10点15分触发|
|0 * 14 * * ?|每天14点0分到14点59分，整分触发|  
|0 0/5 14 * * ?|每天14点0分到14点59分，每5分钟触发|
|0 0/5 14,18 * * ?|每天14点0分到14点59分（18点0分到18点59分），每隔5分钟触发|
|0 0-5 14 * * ?|每天14点0分到14点5分，整分触发|
|0 10,44 14 ? 3 WED|每年3月的每周3的14点10分或14点44分触发|  
|0 59 2 ? * FRI|每周五的2点59分触发|
|0 15 10 ? * MON-FRI|周一到周五的10点15分触发|
|0 15 10 15 * ?|每月的15号的10点15分触发|
|0 15 10 L * ?|每个月的最后一天的10点15分触发|
|0 15 10 ? * 6L|每个月的最后一个星期五的10点15分触发|
|0 15 10 ? * 6L 2002-2005|2002年到2005年的每个月的最后一个星期五的10点15分触发|
|0 15 10 ? * 6#3|每个月的第三个星期五的10点15分触发|
|0 0 12 1/5 * ?|每个月从1号开始到月末，每隔5天的早上12点整触发|
|0 11 11 11 11 ?|11月11号11点11分触发|
|0 51 15 26 4 ? 2016|2016年的4月26号15点51分触发|
