package philosopher.paradise.controller;

import io.swagger.annotations.*;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.Random;

@Api(tags="Quote Controller", description = "allows to browse, select and sort the quotes database")
@CrossOrigin
@Controller
public class QuoteController {

    private final QuoteServiceImpl service;
    private final TopicServiceImpl topicService;
    private final PhilosopherServiceImpl philosopherService;

    @Autowired
    public QuoteController(QuoteServiceImpl service, TopicServiceImpl topicService, PhilosopherServiceImpl philosopherService) {
        this.service = service;
        this.topicService = topicService;
        this.philosopherService = philosopherService;
    }

    @ApiOperation(value="Returns the list of all quotes in the database", response = Iterable.class)
    @GetMapping(path="/quotes")
    public String getAllQuotes(Model model){
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "quotes";
    }

    @ApiOperation(value="Returns the list of all quotes by a particular topic", response = Iterable.class)
    @GetMapping(path="/topics/{id}")
    public String getQuotesByTopic(@PathVariable(value="id") Long id, Model model){
        Topic topic = topicService.getTopicById(id);
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("topic", topic);
        model.addAttribute("quotes_by_topic", service.getQuotesByTopic(topic));
        model.addAttribute("categories", Category.values());
        return "topic";
    }

    @ApiOperation(value="Returns the list of all quotes by a particular Philosopher", response = Iterable.class)
    @GetMapping({"/quotes/{id}"})
    public String getPhilosopherQuotes(@PathVariable(value="id") Long id, Model model){
        model.addAttribute("quotes", service.getQuotesByPhilosopherId(id));
        model.addAttribute("categories", Category.values());
        return "philosopher_quotes";
    }

    @ApiOperation(value="Returns the form page to add a new quote")
    @GetMapping({"/quoteAdd"})
    public String quoteAdd(Model model) {
        Quote quote = new Quote();
        quote.setId(new Random().nextLong()+20015);
        model.addAttribute("quote", quote);
        model.addAttribute("categories", Category.values());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("philosophers", philosopherService.getPhilosophers());
        return "quote";
    }

    @ApiIgnore
    @PostMapping("/quoteAdd")
    public String addQuote(Model model, Quote quote) {
        service.createQuote(quote);
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("quote", service.getQuoteById(quote.getId()));
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

    @ApiOperation(value="Deletes a quote")
    @GetMapping("/quoteDelete/{id}")
    public String deleteQuote(@PathVariable(required=true, name="id") Long id, Model model){
        service.deleteQuote(id);
        model.addAttribute("quotes", service.getQuotes());
        model.addAttribute("topics", topicService.getTopics());
        model.addAttribute("categories", Category.values());
        return "quotes";
    }

}