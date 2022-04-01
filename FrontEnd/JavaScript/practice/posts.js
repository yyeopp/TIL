$(function () {
  let initialRequest = { pageNo: 0, amount: 10 };

  // 계속 쓰게 될 전역 변수
  let totalPage;
  let totalPost;

  // 시작 페이지는 따로 오더를 내리는 수 밖에

  $.ajax({
    url: "post",
    type: "GET",
    data: initialRequest,
    contentType: "application/json; charset=utf-8",
    success: function (response) {
      console.log(response);
      initiatePost(response.data);
    },
    error: function (response) {
      console.log("error");
      console.log(response);
    },
  });

  function initiatePost(postData) {
    totalPost = postData.total;

    totalPage = Math.ceil(totalPost / 10);
    pagination(1);
    expressListOutline(0, totalPost);
    expressPost(postData.posts);
  }

  function pagination(firstPage) {
    $("#post-list-page").empty();
    $("#post-list-page").append(`
        <li>
          <button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple"
                    aria-label="Previous" id="post-list-btn-prev" value="${firstPage}">
            <svg aria-hidden="true" class="w-4 h-4 fill-current" viewBox="0 0 20 20">
            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
                        clip-rule="evenodd" fill-rule="evenodd"></path>
            </svg>
          </button>
        </li>
      `);
    for (let i = firstPage; i < firstPage + 10; i++) {
      if (i > totalPage) break;
      $("#post-list-page").append(`
        <li>
          <button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple" id="presentPage">${i}</button>
        </li>
      `);
    }
    $("#post-list-page").append(`
      <li>
        <button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple"
                    aria-label="Next" id="post-list-btn-next" value="${firstPage}">
          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
          <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                        clip-rule="evenodd" fill-rule="evenodd"></path>
          </svg>
        </button>
      </li>
    `);
  }

  function getPostData(request, selectedPage) {
    $.ajax({
      url: "post",
      type: "GET",
      data: request,
      contentType: "application/json; charset=utf-8",
      success: function (response) {
        expressListOutline(selectedPage);
        expressPost(response.data.posts);
      },
      error: function (response) {
        console.log("error");
        console.log(response);
      },
    });
  }

  // 페이지네이션에서 특정 페이지를 직접 선택한 경우에 대한 ajax
  $(document).on("click", "#presentPage", function () {
    let selectedPage = $(this).html() * 10 - 10;
    // 0, 10, 20 순서로 찍히게 되어있다 - 각각 1, 2, 3 페이지에 해당
    let postRequest = {
      pageNo: selectedPage,
      amount: 10,
    };

    getPostData(postRequest, selectedPage);
  });

  $(document).on("click", "#post-list-btn-next", function () {
    let selectedPage = ($(this).val() * 1 + 10) * 10 - 10;
    if (selectedPage < totalPost) {
      let postRequest = {
        pageNo: selectedPage,
        amount: 10,
      };
      pagination($(this).val() * 1 + 10);
      getPostData(postRequest, selectedPage);
    }
  });

  $(document).on("click", "#post-list-btn-prev", function () {
    let selectedPage = ($(this).val() * 1 - 10) * 10 - 10;
    if (selectedPage >= 0) {
      let postRequest = {
        pageNo: selectedPage,
        amount: 10,
      };
      pagination($(this).val() * 1 - 10);
      getPostData(postRequest, selectedPage);
    }
  });

  function expressListOutline(selectedPage) {
    if (selectedPage + 10 <= totalPost) {
      $("#post-list-outline").empty().append(`
      Showing ${selectedPage + 1}-${selectedPage + 10} of ${totalPost}
      `);
    } else {
      $("#post-list-outline").empty().append(`
      Showing ${selectedPage + 1}-${totalPost} of ${totalPost}
      `);
    }
  }

  function expressPost(posts) {
    $("#post-list").empty();
    for (let i = 0; i < posts.length; i++) {
      $("#post-list").append(`
      <tr class="text-gray-700 dark:text-gray-400" id="view-post">
        <td class="px-4 py-3 text-xs">
          <span
            class="px-2 py-1 font-semibold leading-tight text-blue-700 bg-blue-100 rounded-full dark:bg-blue-700 dark:text-blue-100">${posts[i].id}</span>
        </td>
        <td class="px-4 py-3 text-sm">
          <a href="/posts/${posts[i].id}" th:href="@{/post/${posts[i].id}}" class="font-semibold post_more_btn">
          ${posts[i].title}</a>
        </td>
        <td class="px-4 py-3">
          <div class="flex items-center text-sm">
            <p class="">${posts[i].writer}</p>
          </div>
        </td>
        <td class="px-4 py-3 text-sm">
          ${posts[i].createdDate}
        </td>
      </tr>
      `);
    }
  }
});
