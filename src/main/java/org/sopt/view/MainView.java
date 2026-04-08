package org.sopt.view;

import java.util.Scanner;
import java.util.function.Supplier;
import org.sopt.controller.PostController;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CommonResponse;
import org.sopt.exception.CustomException;

public class MainView {
    private static MainView instance;
    private final Scanner scanner = new Scanner(System.in);
    private final PostController postController = new PostController();
    private boolean running = true;

    public static MainView getInstance() {
        if (instance == null) {
            instance = new MainView();
        }

        return instance;
    }

    public void start() {
        while (running) {
            printMenu();

            int choice = scanner.nextInt();

            scanner.nextLine();

            handleProcessByChoice(choice);
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n=== 에브리타임 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 전체 조회");
        System.out.println("3. 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택: ");
    }

    private void handleProcessByChoice(final int choice) {
        switch (choice) {
            case 1:
                CreatePostRequest createRequest = getCreatePostRequestByInput();
                response(() -> postController.createPost(createRequest));
                break;
            case 2:
                response(postController::getAllPosts);
                break;
            case 3:
                Long postId = getPostIdInputForDetail();
                response(() -> postController.getPost(postId));
                break;
            case 4:
                UpdatePostRequest updateRequest = getUpdatePostRequestByInput();
                response(() -> postController.updatePost(updateRequest));
                break;
            case 5:
                Long deleteId = getPostIdInputForDelete();
                response(() -> postController.deletePost(deleteId));
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("❗ 잘못된 입력입니다.");
        }
    }

    private CreatePostRequest getCreatePostRequestByInput() {
        System.out.print("제목: ");
        String title = scanner.nextLine();
        System.out.print("내용: ");
        String content = scanner.nextLine();
        System.out.print("작성자: ");
        String author = scanner.nextLine();

        return CreatePostRequest.of(title, content, author);
    }

    private Long getPostIdInputForDetail() {
        System.out.print("조회할 게시글 ID: ");
        return scanner.nextLong();
    }

    private UpdatePostRequest getUpdatePostRequestByInput() {
        System.out.print("수정할 게시글 ID: ");
        Long updateId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("새 제목: ");
        String newTitle = scanner.nextLine();
        System.out.print("새 내용: ");
        String newContent = scanner.nextLine();

        return UpdatePostRequest.of(updateId, newTitle, newContent);
    }

    private Long getPostIdInputForDelete() {
        System.out.println("삭제할 게시글 ID: ");
        return scanner.nextLong();
    }

    private void exit() {
        running = false;
        System.out.println("👋 프로그램 종료");
    }

    //공통 응답 뷰 처리 로직

    private void response(Supplier<CommonResponse<?>> responseSupplier) {
        CommonResponse<?> response = handleException(responseSupplier);

        if (!response.isSuccess()) {
            System.out.println(response.clientMessage());
            System.out.println("[Error - " + response.errorMessage() + "]");
            return;
        }

        Object data = response.data();

        if (data == null) {
            System.out.println(response.clientMessage());
            return;
        }

        System.out.println(data);
    }

    private CommonResponse<?> handleException(Supplier<CommonResponse<?>> before) {
        try {
            return before.get();
        } catch (Exception e) {
            if (e instanceof CustomException) {
                return CommonResponse.error(((CustomException) e).getErrorMessage());
            } else {
                return CommonResponse.error(e);
            }
        }
    }
}
