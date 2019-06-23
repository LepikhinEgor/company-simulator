package controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "index";
	}
	
//	@RequestMapping(value = "/get-posts", method = RequestMethod.POST, consumes = "application/json")
//	public @ResponseBody
//	ArrayList<BlogPost> getPosts(@RequestBody PostsQuerryInfo postsShowedInfo) {
//		logger.info("Start method getPosts");
//		ArrayList<BlogPost> posts = new ArrayList<BlogPost>();
//		
//		BlogDBController blogDBController = new BlogDBController();
//		
//		try {
//			posts = blogDBController.getLastPosts(postsShowedInfo.getCurPostsShowed());
//		} catch(SQLException ex) {
//			logger.error("Failed DB Connection");
//			logger.error(ex.toString());
//		}
//		
//		return posts;
//	}
//	
//	@RequestMapping(value = "/write-new-post", method = RequestMethod.POST, consumes = "application/json")
//	public @ResponseBody
//	BlogPost addNewPostToDB(@RequestBody BlogPost post) {
//		logger.info("Start method addNewPostToDB, parameter " + post.toString() );
//		BlogPost newPost = new BlogPost();
//		
//		BlogDBController blogDBController = new BlogDBController();
//		
//		try {
//			newPost = blogDBController.addNewPost(post.getContent());
//		} catch(SQLException ex) {
//			logger.error("Failed DB Connection");
//			logger.error(ex.toString());
//			logger.error(ex.getStackTrace().toString());
//		}
//		
//		return newPost;
//	}
//	
//	@RequestMapping(value = "/delete-post", method = RequestMethod.POST, consumes = "application/json")
//	public @ResponseBody
//	String deletePostFromDB(@RequestBody BlogPost post) {
//		logger.info("Start method deletePostFromDB, parameter " + post.toString() );
//		
//		BlogDBController blogDBController = new BlogDBController();
//		
//		try {
//			blogDBController.deletePost(post.getId());
//		} catch(SQLException ex) {
//			logger.error("Failed DB Connection");
//			logger.error(ex.toString());
//			logger.error(ex.getStackTrace().toString());
//			
//			return "fail";
//		}
//		catch (Exception ex) {
//			logger.error("Unknown error");
//			logger.error(ex.toString());
//			logger.error(ex.getStackTrace().toString());
//			
//			return "fail";
//		}
//		return "success";
//	}
//	
//	@RequestMapping(value = "/like-post", method = RequestMethod.POST, consumes = "application/json")
//	public @ResponseBody
//	String likePostToDB(@RequestBody BlogPost post) {
//		logger.info("Start method likePostToDB, parameter " + post.toString() );
//		
//		BlogDBController blogDBController = new BlogDBController();
//		
//		try {
//			blogDBController.likePost(post.getId());
//		} catch(SQLException ex) {
//			logger.error("Failed DB Connection");
//			logger.error(ex.toString());
//			logger.error(ex.getStackTrace().toString());
//			
//			return "fail";
//		}
//		catch (Exception ex) {
//			logger.error("Unknown error");
//			logger.error(ex.toString());
//			logger.error(ex.getStackTrace().toString());
//			
//			return "fail";
//		}
//		return "success";
//	}
}
