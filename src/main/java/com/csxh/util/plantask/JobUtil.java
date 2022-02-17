package com.csxh.util.plantask;

import static org.quartz.TriggerBuilder.newTrigger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class JobUtil {

    private Scheduler sched;
    
    public Scheduler getSched() {
		return sched;
	}

	public void setSched(Scheduler sched) {
		this.sched = sched;
	}
	
	public void init() {
		try {
			sched.start();
		} catch (SchedulerException e) {
		}
	}
	
	public void close() {
		try {
			sched.shutdown(true);
		} catch (SchedulerException e) {
		}
	}

	public void addJob(String jobName, String jobGroupName, String triggerName,
    		String triggerGroupName, Class<? extends Job> clazz, Date executeTime, Map<String, Object> map) {
        
        JobDetail job = JobBuilder.newJob(clazz)
        		.withIdentity(jobName, jobGroupName)
        		.usingJobData(new JobDataMap(map))
        		.build();
        Trigger trigger = (SimpleTrigger)newTrigger()
        		.withIdentity(triggerName, triggerGroupName)
        		.startAt(executeTime)
        		.build();
        
        try {
			sched.scheduleJob(job, trigger);
			sched.start();
		} catch (SchedulerException e) {
		}
    }
    
    public void modifyJobTime(String triggerName, String triggerGroupName, Date executeTime) {
    	TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
    	
        Trigger trigger = (SimpleTrigger)newTrigger()
        		.withIdentity(triggerName, triggerGroupName)
        		.startAt(executeTime)
        		.build();
    	
    	try {
			sched.rescheduleJob(triggerKey, trigger);
			sched.start();
		} catch (SchedulerException e) {
		}
    }
    
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
 
            sched.pauseTrigger(triggerKey);
            sched.unscheduleJob(triggerKey);
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static InetAddress getLocalHostLANAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本机的ip地址
     */
    public static String getLocalHostAddress() {
        String address = getLocalHostLANAddress().toString();
//        取得的地址为 "/xxx.xx.xx.xx"格式
        if (address.startsWith("/")) {
            address = address.substring(1, address.length());
        }
        return address;
    }
    
}
