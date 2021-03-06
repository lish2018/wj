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
            // ???????????????????????????
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // ??????????????????????????????IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// ??????loopback????????????
                        if (inetAddr.isSiteLocalAddress()) {
                            // ?????????site-local?????????????????????
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local???????????????????????????????????????????????????
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // ?????????????????? non-loopback??????.???????????????????????????
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ???????????????ip??????
     */
    public static String getLocalHostAddress() {
        String address = getLocalHostLANAddress().toString();
//        ?????????????????? "/xxx.xx.xx.xx"??????
        if (address.startsWith("/")) {
            address = address.substring(1, address.length());
        }
        return address;
    }
    
}
