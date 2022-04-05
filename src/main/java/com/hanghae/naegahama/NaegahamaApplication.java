package com.hanghae.naegahama;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
<<<<<<< HEAD

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableScheduling
@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
public class NaegahamaApplication {

    //로컬
    public static final String APPLICATION_LOCATIONS = "spring.config.location="

/*        + "/home/ec2-user/app/deploy/application-local.yml,"
            + "/home/ec2-user/app/deploy/aws.yml";*/

             + "classpath:application-local.yml,"
            + "classpath:aws.yml";





    public static void main(String[] args)
    {
        new SpringApplicationBuilder(NaegahamaApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

    //ec2 자동배포

   /* public static void main(String[] args) {
        SpringApplication.run(NaegahamaApplication.class, args);
    }
*/
   @Bean
    public TaskScheduler taskScheduler() {

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(2);

        return taskScheduler;
    }
}
