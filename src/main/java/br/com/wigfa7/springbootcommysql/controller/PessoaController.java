package br.com.wigfa7.springbootcommysql.controller;

import br.com.wigfa7.springbootcommysql.controller.dto.PessoaRq;
import br.com.wigfa7.springbootcommysql.controller.dto.PessoaRs;
import br.com.wigfa7.springbootcommysql.model.Pessoa;
import br.com.wigfa7.springbootcommysql.repository.PessoaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
    @GetMapping("/all")
    public List<PessoaRs> findAll(){
        var pessoas  = pessoaRepository.findAll();
        return pessoas
                .stream()
                .map((p)-> PessoaRs.converter(p))
                .collect(Collectors.toList());
    }
    @GetMapping("/details/{id}")
    public PessoaRs findById(@PathVariable("id") Long id){
        var pessoa = pessoaRepository.getById(id);
        return PessoaRs.converter(pessoa);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id){
       pessoaRepository.deleteById(id);
    }

    @PostMapping("/add")
    public void savePerson(@RequestBody PessoaRq pessoa){
        var p = new Pessoa();
        p.setId(0);
        p.setNome(pessoa.getNome());
        p.setSobrenome(pessoa.getSobrenome());
        pessoaRepository.save(p);
    }
    @PutMapping("/update/{id}")
    public void updatePerson(@PathVariable Long id, @RequestBody PessoaRq pessoa){
        if(pessoaRepository.existsById(id)) {
            var p = new Pessoa();
            p.setId(id);
            p.setNome(pessoa.getNome());
            p.setSobrenome(pessoa.getSobrenome());
            pessoaRepository.save(p);
        }
    }
}
