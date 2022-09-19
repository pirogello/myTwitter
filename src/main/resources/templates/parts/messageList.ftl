<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager url page />
<div class="card-columns" id="message-list">
    <#list page.content as massage>
        <div class="card my-3" data-id="${massage.id}">
            <#if massage.filename??>
                <img class="card-img-top" src="/img/${massage.filename}"/>
            </#if>
            <div class="m-2">
                <span>${massage.text}</span><br/>
                <i>#${massage.tag}</i>
            </div>
            <div class="card-footer text-muted container">
                <div class="row">
                   <a class="col align-self-center" href="/user-messages/${massage.author.id}">${massage.authorName}</a>
                    <a class="col align-self-center" href="/messages/${massage.id}/like">
                        <#if !massage.meLiked>
                            <i class="fa-regular fa-heart"></i><#--пустое-->
                        <#else>
                            <i class="fa-solid fa-heart"></i><#--заполнено-->
                        </#if>
                        ${massage.likes}
                    </a>
                    <#if massage.author.id == currentUserId>
                        <#if isCurrentUser?? && isCurrentUser>
                        <a class="col btn btn-primary" href="/user-messages/${massage.author.id}?massage=${massage.id}">
                            Edit
                        </a>
                        </#if>
                    </#if>
                </div>
            </div>
        </div>

    <#else>
        No massage
    </#list>
</div>

<@p.pager url page />