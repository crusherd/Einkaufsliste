require 'test_helper'

class StoreTest < ActiveSupport::TestCase
  test "required attributes" do
    store = Store.new
    assert !store.valid?
    assert store.errors.get(:name)
    assert !store.errors.get(:address)
  end
  
  test "unique_store" do
    store = Store.new(:name => "Rewe")
    assert !store.save
  end
  
  test "create and delete a store" do
    store = Store.new(:name => "Penny")
    
    assert store.save
    
    assert store.delete
  end
  
  test "associate article with store" do
    store = stores(:Rewe)
    article = articles(:Kaese)
    
    cnt_references_to_article = store.articles.count
    
    store.articles << article
    
    assert_not_equal(cnt_references_to_article, store.articles.count)
  end
  
  test "associate store with address" do
    store = stores(:Rewe)
    address = addresses(:Abcweg)
    
    cnt_references_to_address = store.addresses.count
    
    store.addresses << address
    
    assert_not_equal(cnt_references_to_address, store.addresses.count)
  end
end
