package philosopher.paradise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import philosopher.paradise.dto.QuoteDTO;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;
import philosopher.paradise.service.QuoteServiceImpl;
import philosopher.paradise.service.TopicServiceImpl;

@Controller
public class QuoteController {

    private QuoteServiceImpl service;
    private final TopicServiceImpl topicService;


    @Autowired
    public QuoteController(QuoteServiceImpl service, TopicServiceImpl topicService) {
        this.service = service;
        this.topicService = topicService;
    }

    @GetMapping(path="/quotes")
    public String getAllQuotes(Model model){
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "quotes";
    }

    @GetMapping(path="/topics/{id}")
    public String getQuotesByTopic(@PathVariable(value="id") Long id, Model model){
        Topic topic = topicService.getTopicById(id);
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("topic", topic);
        model.addAttribute("quotes_by_topic", service.getQuotesByTopic(topic));
        model.addAttribute("categories", Category.values());
        return "topic";
    }

    @GetMapping({"/quotes/{id}"})
    public String getPhilosopherQuotes(@PathVariable(value="id") Long id, Model model){
        model.addAttribute("quotes", service.getQuotesByPhilosopherId(id));
        model.addAttribute("categories", Category.values());
        return "philosopher_quotes";
    }

    @PostMapping("/quotes/quote")
    public String addQuote(@ModelAttribute QuoteDTO dto, Model model){
        QuoteDTO dtoToAdd = service.createQuote(dto);
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "quote";
    }

    @PutMapping("/quotes/{id}")
    public String updateQuote(@PathVariable(value="id") Long id, @RequestBody String text, Model model){
        service.updateQuote(id, text);
        return "/quotes";
    }

    @GetMapping("/quoteDelete/{id}")
    public String deleteQuote(@PathVariable(required=true, name="id") Long id, Model model){
        service.deleteQuote(id);
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "quotes";
    }

}
