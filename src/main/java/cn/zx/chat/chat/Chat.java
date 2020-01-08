package cn.zx.chat.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.InetAddress;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 15:35 2019/5/21 0021
 */
@Controller
public class Chat{
    @GetMapping("/chat/{username}")
    public String chatIndex(Model model, @PathVariable("username") String username) throws Exception{
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        model.addAttribute("username",username);
        model.addAttribute("hostAddress",hostAddress);
        return "chat";
    }
}
