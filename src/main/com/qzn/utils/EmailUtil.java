package com.qzn.utils;

import java.util.concurrent.LinkedBlockingQueue;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.qzn.helper.ApplicationContextHelper;
import com.qzn.models.Email;

public final class EmailUtil {
	public static LinkedBlockingQueue<JavaMailSender> mailSenderQueue;

	private static void initMailSenderQueue(int numberOfMailSender) {
		mailSenderQueue = new LinkedBlockingQueue<JavaMailSender>(numberOfMailSender);
		for (int i = 0; i < numberOfMailSender; i++) {
			JavaMailSender mailSender = getJavaMailSender();
			mailSenderQueue.add(mailSender);
		}
	}

	public static JavaMailSender getJavaMailSender() {
		JavaMailSender mailSender = ApplicationContextHelper.getInstance().getBean(JavaMailSender.class, "mailSender");
		return mailSender;
	}

	public static void sendEmail(Email email) throws Exception {
		Integer mailQueueNum = Integer.valueOf(PropertyUtil.getPropertyValue("mail.queue.num"));
		if (mailSenderQueue == null || mailSenderQueue.size() == 0) {
			initMailSenderQueue(mailQueueNum);
		}
		JavaMailSender mailSender = mailSenderQueue.take();
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, email.getEncoding());
			helper.setFrom(email.getFromEmailAddress(), email.getFromPersonName());
			helper.setTo(email.getToEmailAddresses());
			helper.setSubject(email.getSubject());
			helper.setText(email.getContent());
			if (email.getAttachment() != null) {
				if (email.getAttachment().exists()) {
					helper.addAttachment(MimeUtility.encodeWord(email.getAttachment().getName(), "UTF-8", null),
							email.getAttachment());
				}
			}
			mailSender.send(message);
		} finally {
			mailSenderQueue.put(mailSender);
		}
	}
}
