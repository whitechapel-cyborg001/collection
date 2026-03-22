package net.vys.collection.specifications;
import net.vys.collection.entities.Comic;

import org.springframework.data.jpa.domain.Specification;

public class ComicSpecification {
    public static Specification<Comic> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null ? null : 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Comic> hasAuthor(Long authorId) {
        return (root, query, cb) ->{
            if (authorId == null) return null;
            query.distinct(true);
            return cb.equal(root.join("authors").get("id"), authorId);
        };
    }

    public static Specification<Comic> hasSerie(Long serieId) {
        return (root, query, cb) ->
            serieId == null ? null : cb.equal(root.get("serie").get("id"), serieId);
    }

    public static Specification<Comic> hasPublisher(Long publisherId) {
        return (root, query, cb) ->
            publisherId == null ? null : cb.equal(root.get("publisher").get("id"), publisherId);
    }
}
