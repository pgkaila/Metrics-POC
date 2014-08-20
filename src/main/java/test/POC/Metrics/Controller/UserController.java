package test.POC.Metrics.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import test.POC.Metrics.Domain.User;
import test.POC.Metrics.Services.UserService;

@Controller
public class UserController {

	private UserService userService;
    
    @Autowired(required=true)
    @Qualifier(value="userService")
    public void setUserService(UserService ps){
        this.userService = ps;
    }
     
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("person", new User());
        model.addAttribute("listUsers", this.userService.listUsers());
        return "person";
    }
     
    //For add and update person both
    @RequestMapping(value= "/person/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("person") User p){
         
        if(p.getId() == 0){
            //new person, add it
            this.userService.addUser(p);
        }else{
            //existing person, call update
            this.userService.updateUser(p);
        }
         
        return "redirect:/persons";
         
    }
     
    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id){
         
        this.userService.removeUser(id);
        return "redirect:/persons";
    }
  
    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.userService.getUserById(id));
        model.addAttribute("listUsers", this.userService.listUsers());
        return "person";
    }
}
