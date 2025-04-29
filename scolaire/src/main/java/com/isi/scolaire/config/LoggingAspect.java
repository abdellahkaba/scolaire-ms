package com.isi.scolaire.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }
    @Pointcut("within(com.isi.scolaire..*) || within(com.isi.scolaire.student.StudentController) ||" +
            "within(com.isi.scolaire.administrativeAgent.AdministrativeAgentController) || within(com.isi.scolaire.classes.ClasseController) ||" +
            "within(com.isi.scolaire.courses.CourseController) || within(com.isi.scolaire.halfYearly.HalfYearlyController) ||" +
            "within(com.isi.scolaire.kind.KindController) || within(com.isi.scolaire.program.ProgramController) ||" +
            "within(com.isi.scolaire.registration.RegistrationController) || within(com.isi.scolaire.sectors.SectorController) ||" +
            "within(com.isi.scolaire.sessions.SessionController) || within(com.isi.scolaire.student.StudentController) ||" +
            "within(com.isi.scolaire.subjects.SubjectController) || within(com.isi.scolaire.teacher.TeacherController)"
    )
    public void applicationPackagePointcut() {
    }
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getMessage());
    }
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Object result = joinPoint.proceed();
        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
        return result;
    }
}

