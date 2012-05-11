class ShoppingListsController < ApplicationController
  # GET /shopping_lists
  # GET /shopping_lists.json
  def index
    @shopping_lists = ShoppingList.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @shopping_lists }
    end
  end

  # GET /shopping_lists/1
  # GET /shopping_lists/1.json
  def show
    @shopping_list = ShoppingList.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @shopping_list }
    end
  end

  # GET /shopping_lists/new
  # GET /shopping_lists/new.json
  def new
    @shopping_list = ShoppingList.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @shopping_list }
    end
  end

  # GET /shopping_lists/1/edit
  def edit
    @shopping_list = ShoppingList.find(params[:id])
  end

  # POST /shopping_lists
  # POST /shopping_lists.json
  def create
    @shopping_list = ShoppingList.new(params[:shopping_list])

    respond_to do |format|
      if @shopping_list.save
        format.html { redirect_to @shopping_list, notice: 'Shopping list was successfully created.' }
        format.json { render json: @shopping_list, status: :created, location: @shopping_list }
      else
        format.html { render action: "new" }
        format.json { render json: @shopping_list.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /shopping_lists/1
  # PUT /shopping_lists/1.json
  def update
    @shopping_list = ShoppingList.find(params[:id])

    respond_to do |format|
      if @shopping_list.update_attributes(params[:shopping_list])
        format.html { redirect_to @shopping_list, notice: 'Shopping list was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @shopping_list.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /shopping_lists/1
  # DELETE /shopping_lists/1.json
  def destroy
    @shopping_list = ShoppingList.find(params[:id])
    @shopping_list.destroy

    respond_to do |format|
      format.html { redirect_to shopping_lists_url }
      format.json { head :no_content }
    end
  end
end
