package philosopher.paradise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import philosopher.paradise.dto.QuoteDTO;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;
import philosopher.paradise.service.PhilosopherServiceImpl;
import philosopher.paradise.service.QuoteServiceImpl;
import philosopher.paradise.service.TopicServiceImpl;

import java.util.Random;

@Controller
public class QuoteController {

    private final QuoteServiceImpl service;
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

    @GetMapping({"/quoteAdd"})
    public String quoteAdd(Model model) {
        Quote quote = new Quote();
        quote.setId(new Random().nextLong()+20015);
        model.addAttribute("quote", quote);
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        return "quote";
    }

    @PostMapping("/quoteAdd")
    public String addQuote(Model model, Quote quote) {
        service.createQuote(quote);
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("quote", service.getQuoteById(quote.getId()));
        //model.addAttribute("philosopher", service.getQuoteById(id).getPhilosopher());
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        return "redirect:/quotes";
    }

    @GetMapping({"/quoteEdit","/quoteEdit/{id}"})
    public String quoteEditForm(Model model, @PathVariable(required = false, name = "id") Long id) {
        model.addAttribute("quote", service.getQuoteById(id));
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("quote", service.getQuoteById(id));
        return "quoteForm";
    }

    @PostMapping("/quoteEdit")
    public String quoteEdit(Model model, Long id, String text) {
        service.updateQuote(id, text);
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("quote", service.getQuoteById(id));
        model.addAttribute("philosopher", service.getQuoteById(id).getPhilosopher());
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        return "redirect:/quotes";
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
