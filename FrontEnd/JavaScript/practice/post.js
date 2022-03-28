$(function () {
  let initialRequest = { pageNo: 0, amount: 10 };
  // let tempPage;

  $.ajax({
    url: "post",
    type: "GET",
    data: initialRequest,
    contentType: "application/json; charset=utf-8",
    success: function (response) {
      initiatePost(response.data);
    },
    error: function (response) {
      console.log("error");
      console.log(response);
    },
  });

  function initiatePost(postData) {
    let totalPost = postData.total;

    let totalPage = Math.ceil(totalPost / 10);
    initialPage(totalPage);
    expressListOutline(0, totalPost);
    expressPost(postData.posts);
  }

  function initialPage(totalPage) {
    $("#post-list-page").empty();
    $("#post-list-page").append(`
        <li>
          <button class="px-3 py-1 rounded-md rounded-l-lg focus:outline-none focus:shadow-outline-purple"
                    aria-label="Previous" id="post-list-btn-prev">
            <svg aria-hidden="true" class="w-4 h-4 fill-current" viewBox="0 0 20 20">
            <path d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
                        clip-rule="evenodd" fill-rule="evenodd"></path>
            </svg>
          </button>
        </li>
      `);
    for (let i = 1; i <= totalPage; i++) {
      if (i > 10) break;
      $("#post-list-page").append(`
        <li>
          <button class="px-3 py-1 rounded-md focus:outline-none focus:shadow-outline-purple" id="presentPage">${i}</button>
        </li>
      `);
    }
    $("#post-list-page").append(`
      <li>
        <button class="px-3 py-1 rounded-md rounded-r-lg focus:outline-none focus:shadow-outline-purple"
                    aria-label="Next" id="post-list-btn-next">
          <svg class="w-4 h-4 fill-current" aria-hidden="true" viewBox="0 0 20 20">
          <path d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                        clip-rule="evenodd" fill-rule="evenodd"></path>
          </svg>
        </button>
      </li>
    `);
  }

  // function markingPage(temp, present) {}

  $(document).on("click", "#presentPage", function () {
    // tempPage = $(this)[0].outerText;
    // console.log(tempPage[0].outerText);
    // $(this).attr(
    //   "class",
    //   "px-3 py-1 text-white transition-colors duration-150 bg-purple-600 border border-r-0 border-purple-600 rounded-md focus:outline-none focus:shadow-outline-purple"
    // );

    // console.log($(this).html());

    let selectedPage = $(this).html() * 10 - 10;
    let postRequest = {
      pageNo: selectedPage,
      amount: 10,
    };

    $.ajax({
      url: "post",
      type: "GET",
      data: postRequest,
      contentType: "application/json; charset=utf-8",
      success: function (response) {
        expressPost(response.data.posts);
      },
      error: function (response) {
        console.log("error");
        console.log(response);
      },
    });
  });

  function expressListOutline(start, totalPost) {
    $("#post-list-outline").empty().append(`
      Showing ${start + 1}-${start + 10} of ${totalPost}
    `);
  }

  function expressPost(posts) {
    $("#post-list").empty();
    for (let i = 0; i < posts.length; i++) {
      $("#post-list").append(`
      <tr class="text-gray-700 dark:text-gray-400">
        <td class="px-4 py-3 text-xs">
          <span
            class="px-2 py-1 font-semibold leading-tight text-blue-700 bg-blue-100 rounded-full dark:bg-blue-700 dark:text-blue-100">
            ${posts[i].id}
          </span>
        </td>
        <td class="px-4 py-3 text-sm">
          <a href="javascript: viewPost(${posts[i].id})" th:href="@{~/}" class="font-semibold post_more_btn">
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
