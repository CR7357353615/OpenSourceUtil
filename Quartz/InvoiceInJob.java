package cn.com.servyou.vtax.common.jobs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.servyou.vtax.common.vo.SysOperator;
import cn.com.servyou.vtax.common.vo.VATInvoiceInSummary;
import cn.com.servyou.vtax.common.vo.VatInvoiceInMonitor;
import cn.com.servyou.vtax.invoicein.service.IInvoiceInMonitorService;

@Component
public class InvoiceInJob extends QuartzJobBean {

    @Autowired
    IInvoiceInMonitorService invoiceInMonitorService;

    private static InvoiceInJob invoiceInJob;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceInJob.class);

    @PostConstruct
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
    	
        SysOperator operator = new SysOperator();
        operator.setCode("系统");
        // 获取需要补全的Summary发票
        List<VATInvoiceInSummary> summaryList = invoiceInJob.invoiceInMonitorService.getInvoiceInSummary();
        
        //查询所有监控中的发票
        List<VatInvoiceInMonitor> overDueMonitorInvoices=invoiceInJob.invoiceInMonitorService.queryOverDueMonitor();
        //监控已过期则取消监控
        invoiceInJob.invoiceInMonitorService.delInvoiceInMonitor(overDueMonitorInvoices);
        

        // 补全发票
        if (null != summaryList && summaryList.size() != 0) {
            invoiceInJob.invoiceInMonitorService.complation(summaryList, operator);
         // 获取符合条件的监控发票
            List<VatInvoiceInMonitor> monitorList = invoiceInJob.invoiceInMonitorService.getInvoiceInMonitor();
            invoiceInJob.invoiceInMonitorService.updateInvoiceInMonitor(monitorList, operator);
        }
        
       
    }
}
