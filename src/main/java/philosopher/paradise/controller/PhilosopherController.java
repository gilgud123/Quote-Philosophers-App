package philosopher.paradise.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import philosopher.paradise.entity.Category;
import philosopher.paradise.service.PhilosopherServiceImpl;
import philosopher.paradise.service.QuoteServiceImpl;
import philosopher.paradise.service.TopicServiceImpl;

import java.util.Locale;

@CrossOrigin
@Controller
public class PhilosopherController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PhilosopherController.class);

    private final PhilosopherServiceImpl service;
    private final QuoteServiceImpl quoteService;
    private final TopicServiceImpl topicService;

    @Autowired
    public PhilosopherController(PhilosopherServiceImpl service, QuoteServiceImpl quoteService, TopicServiceImpl topicService) {
        this.service = service;
        this.quoteService = quoteService;
        this.topicService = topicService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page with a philosopher list");
        model.addAttribute("random_quote", quoteService.getRandomQuote());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "index";
    }

    @RequestMapping({"/philosophers"})
    public String getPhilosophers(Model model) {
        log.debug("Getting Index page with a philosopher list");
        model.addAttribute("philosophers", service.getPhilosophers());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "philosophers";
    }

    @GetMapping({"/philosophers/{id}"})
    public String getPhilosopher(@PathVariable(value="id") Long id, Model model){
        log.debug("Getting philosopher by id");
        model.addAttribute("philosopher", service.findById(id));
        model.addAttribute("quotes", quoteService.getQuotesByPhilosopherId(id));
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "philosopher";
    }

    @GetMapping("/categories/{category}")
    public String getPhilosophersPerCategory(@PathVariable(value="category") String category, Model model){
        model.addAttribute("philosophers", service.getPhilosopherByCategory(category));
        model.addAttribute("category", Category.valueOf(category));
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        return "category";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model){
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        return "contact";
    }
}
