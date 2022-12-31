package tk.mwacha.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import tk.mwacha.grpc.*;
import tk.mwacha.repositories.CategoryRepository;

import java.util.UUID;


public class CategoryService extends  CategoryServiceGrpc.CategoryServiceImplBase {
    private CategoryRepository categoryRepository;

    public CategoryService() {
        this.categoryRepository = new CategoryRepository();
    }

    @Override
    public void create(final Category entity, final StreamObserver<Category> responseObserver)  {

        try {
            Category.Builder category = categoryRepository.create(entity);
            responseObserver.onNext(Category.newBuilder().setId(category.getId()).setName(category.getName()).setDescription(category.getDescription()).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void list(Empty request, StreamObserver<CategoryList> responseObserver) {
        try {
            var categories  = categoryRepository.list();
            var categoryList = CategoryList.newBuilder();

            categories.forEach(category -> {
                categoryList.addCategories(Category.newBuilder()
                        .setId(category.getId())
                        .setName(category.getName())
                        .setDescription(category.getDescription())
                        .build());
            });
            responseObserver.onNext(categoryList.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getCategory(CategoryGetRequest request,
                            StreamObserver<Category> responseObserver) {
        try {
            var category  = categoryRepository.findById(UUID.fromString(request.getId()));

            responseObserver.onNext(category.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCategory(DeleteCategoryRequest request, StreamObserver<Category> responseObserver) {

        final var id = request.getId();
        try{
            int result = categoryRepository.deleteById(UUID.fromString(id));

            if (result < 1) {
                throw new RuntimeException();
            }

            responseObserver.onNext(
                    Category.newBuilder()
                            .setId(id)
                            .build()
            );
            responseObserver.onCompleted();
        }catch (Exception e){

            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Category not found for id: " + id)
                            .augmentDescription(e.getLocalizedMessage())
                            .asRuntimeException()
            );
        }
    }
}
