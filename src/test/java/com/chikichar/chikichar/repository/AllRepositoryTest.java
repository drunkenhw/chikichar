package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.*;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.BoardType;
import com.chikichar.chikichar.model.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class AllRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("더미 데이터를 insert 한다")
    public void dummyTest() {

        Member member = Member.builder()
                .email("dumm3y@dummy.com")
                .name("han")
                .address(Address.builder()
                        .streetAddress("busan")
                        .detailAddress("street")
                        .zipcode("13213")
                        .build())
                .password("dsadasdsad")
                .memberRole(MemberRole.ADMIN)
                .build();
        Member saveMember = memberRepository.save(member);

        Board board = new Board("드라이브코스", BoardType.MAP);

        boardRepository.save(board);

        Article article = Article.builder()
                .address(Address.builder().zipcode("12313").streetAddress("거리").detailAddress("서울").build())
                .member(saveMember)
                .board(board)
                .title("제목")
                .content("내용")
                .locationX(32.2323)
                .locationY(32.4321312)
                .build();
        Article saveArticle = articleRepository.save(article);

        Item item = Item.builder()
                .article(saveArticle)
                .madeAt("DIY")
                .price(101000)
                .build();
        Item saveItem = itemRepository.save(item);

        Comment comment = Comment.of(saveArticle, saveMember, "좋아요");
        Comment saveComment = commentRepository.save(comment);

        ArticleImage articleImage = ArticleImage.of("grim", "uuid", "path", saveArticle);
        articleImage.addInArticle(saveArticle);
        ArticleImage saveImage = articleImageRepository.save(articleImage);

        ArticleImage articleImage1 = ArticleImage.of("grim", "uuid", "path", saveArticle);
        articleImage1.addInArticle(saveArticle);
        ArticleImage saveImage2 = articleImageRepository.save(articleImage1);

        Recommend recommend = Recommend.of(saveMember, saveArticle);
        Recommend saveRecommend = recommendRepository.save(recommend);

        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThat(saveArticle.getId()).isEqualTo(article.getId());
        assertThat(saveItem.getId()).isEqualTo(item.getId());
        assertThat(saveComment.getId()).isEqualTo(comment.getId());
        assertThat(saveImage.getId()).isEqualTo(articleImage.getId());
        assertThat(saveRecommend.getId()).isEqualTo(recommend.getId());

    }


}