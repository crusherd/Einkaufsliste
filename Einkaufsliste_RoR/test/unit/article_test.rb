require 'test_helper'

class ArticleTest < ActiveSupport::TestCase
  test "required attributes" do
    article = Article.new
    assert !article.valid?
    assert article.errors.get(:name)
    assert !article.errors.get(:price)
  end
  
  test "unique_article" do
    article = Article.new(:name => "Gruyer (150g)", :price => "3.89")
    assert !article.save
  end
  
  test "create and delete an Article" do
    article = Article.new(:name => "test1", :price => "1.11")
    
    assert article.save
    
    assert article.delete               
  end
  
  test "associate store with article" do
    article = articles(:Kaese)
    store = stores(:Rewe)
    
    cnt_references_to_store = article.stores.count
    
    article.stores << store
    
    assert_not_equal(cnt_references_to_store, article.stores.count)
  end
end
