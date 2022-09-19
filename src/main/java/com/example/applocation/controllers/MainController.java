package com.example.applocation.controllers;

import com.example.applocation.domain.Massage;
import com.example.applocation.domain.User;
import com.example.applocation.domain.dto.MassageDto;
import com.example.applocation.repos.MassageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MassageRepo massageRepo;


    @GetMapping
    public String greeting(Map<String,Object> model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @AuthenticationPrincipal User user
    ){
        Page<MassageDto> page;// = massageRepo.findAll();

        if(filter != null && !filter.isEmpty())
            page = massageRepo.findByTag(filter, pageable, user);
        else
            page = massageRepo.findAll(pageable, user);
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @Valid Massage massage,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file,
                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) throws IOException {
        massage.setAuthor(user);

        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("massage", massage);
        } else {
            saveFile(massage, file);
            model.addAttribute("massage", null);
            massageRepo.save(massage);
        }
            Page<MassageDto> massages = massageRepo.findAll(pageable, user);
            model.addAttribute("page", massages);
            model.addAttribute("url", "/main");

        return "main";
    }

    private void saveFile(Massage massage, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean cf = uploadDir.mkdirs();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + '.' + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            massage.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(name="massage",required = false) Massage massage,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
       // Set<Massage> messages = user.getMassages();
        Page<MassageDto> page = massageRepo.findByAuthor(user, pageable, currentUser);

        model.addAttribute("subscriptionsCount", user.getSubscription().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("userChannel", user);
        //model.addAttribute("messages", messages);

        model.addAttribute("page", page);
        model.addAttribute("url", "/user-messages/" + user.getId());

        model.addAttribute("massage", massage);
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam(name="massage",required = false) Massage massage,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if(massage.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(text)){
                massage.setText(text);
            }
            if(!StringUtils.isEmpty(tag)){
                massage.setTag(tag);
            }
            saveFile(massage, file);
            massageRepo.save(massage);
        }
        return "redirect:/user-messages/" + user;
    }


    @GetMapping("messages/{massage}/like")
    public String like(@AuthenticationPrincipal User currentUser,
                       @PathVariable Massage massage,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer
    ){
        Set<User> likes = massage.getLikes();
        if(likes.contains(currentUser)){
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        return "redirect:" + components.getPath();
    }
}
