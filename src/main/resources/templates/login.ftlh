<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<#--${massage!""}-->
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if massage??>
        <div class="alert alert-${massageType}" role="alert">
            ${massage}
        </div>
    </#if>
<@l.login "/login" false/>
</@c.page>