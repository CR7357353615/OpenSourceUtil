# Quartz使用
---
* 1.定义一个Job继承自org.springframework.scheduling.quartz.QuartzJobBean，并加上@Component注解，交给Spring管理。
* 2.重写executeInternal方法，方法中就是定时任务需要执行的代码。

### 遇到的问题
* Q:在Job中注入service，不能直接调用service的方法
* A：定义一个静态变量，并且在构造方法结束前将invoiceInJob指向this引用。，调用service时以“变量.service.method”的形式调用。

示例：
```java
@Component
public class InvoiceInJob extends QuartzJobBean {

  @Autowired
  IInvoiceInMonitorService invoiceInMonitorService;

  private static InvoiceInJob invoiceInJob;

  @PostConstruct    // 该注解表示在构造方法后执行
  public void init() {
    invoiceInJob = this;
  }

  @Override
  @Transactional
  protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
    //读取config.properties文件读取quartz.bz字段的值，1则执行补全方法，否则不执行。
    Properties prop = new Properties();     
    try{
      String filePath = this.getClass().getResource("/").getPath()+"config.properties";
      //读取属性文件config.properties
      InputStream in = new BufferedInputStream (new FileInputStream(filePath));
      prop.load(in);     ///加载属性列表
      Iterator<String> it=prop.stringPropertyNames().iterator();
      while(it.hasNext()){
        String key=it.next();
        String value = null;
        if(key.equals("quartz.bz")){
          value = prop.getProperty(key);
          if(null != value && !value.equals("1")){
            LOGGER.info("不执行Quartz");
            return;
          }
        }
      }
      in.close();
    } catch(Exception e){
      LOGGER.error("读取失败");
    }

  // 下面是执行Quartz的内容
  }
}
```
