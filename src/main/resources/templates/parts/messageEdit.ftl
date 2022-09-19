<a class="btn btn-primary mt-3" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Massage editor
</a>
<div class="collapse <#if massage??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type = "text" class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if massage??>${massage.text}</#if>" name = "text" placeholder="Введите сообщение"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type = "text" class="form-control" value="<#if massage??>${massage.tag}</#if>" name = "tag" placeholder="Введите тег"/>
            </div>
            <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
            </#if>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="form-control" id="customFile" name="file"/>
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <button type="submit" class="btn btn-primary ml-2">Save message</button>
        </form>
    </div>
</div>