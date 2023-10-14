<div class="d-flex flex-column text-white bg-dark position-fixed" style="width: 250px; height: 100vh;">
    <ul class="list-unstyled">
        <li class="mb-1">
            <a href='./admin?section=users' class="btn btn-dark w-100 ${param.section == 'users' ? 'active' : ''}" style="color: white;">
                <div class="row align-items-center">
                    <div class="col-2">
                        <i class="fa-solid fa-user"></i>
                    </div>
                    <div class="col-3">
                        <span class='text-start fs-5'>Accounts</span>
                    </div>
                </div>
            </a>
        </li>
        <li class="mb-1">
            <a href="./admin?section=classes" class="btn btn-dark w-100 ${param.section == 'classes' ? 'active' : ''}" style="color: white;">
                <div class="row align-items-center">
                    <div class="col-2">
                        <i class="fa-solid fa-people-line"></i>
                    </div>
                    <div class="col-3">
                        <span class='text-start fs-5'>Classes</span>
                    </div>
                </div>
            </a>
        </li>
        <li class="mb-1">
            <a href="./admin?section=courses" class="btn btn-dark w-100 ${param.section == 'courses' ? 'active' : ''}" style="color: white;">
                <div class="row align-items-center">
                    <div class="col-2">
                        <i class="fa-solid fa-book"></i>
                    </div>
                    <div class="col-3">
                        <span class='text-start fs-5'>Courses</span>
                    </div>
                </div>
            </a>
        </li>
    </ul>
</div>